package org.jiucai.appframework.base.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultServiceUnavailableRetryStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Apache Httpclient 工具包装类 <br/>
 * required httpclient-4.3
 * @author jiucai
 *
 */
public class HttpClientUtil {
	public static final String CHARSET_UTF8 = "UTF-8";
//	public static final String CHARSET_GBK = "GBK";
//	public static final String SSL_DEFAULT_SCHEME = "https";
//	public static final int SSL_DEFAULT_PORT = 443;
	
	protected static Log logger = LogFactory.getLog(HttpClientUtil.class);
	
	
	// 使用ResponseHandler接口处理响应，HttpClient使用ResponseHandler会自动管理连接的释放，解决了对连接的释放管理
	private static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
		// 自定义响应处理
		public String handleResponse(HttpResponse response)	throws ClientProtocolException, IOException {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				Charset charset = ContentType.getOrDefault(entity).getCharset();
				if(null != charset){
					return new String(EntityUtils.toByteArray(entity), charset);
				}else{
					return new String(EntityUtils.toByteArray(entity));
				}
				
			} else {
				return null;
			}
		}
	};
	/**
	 * Get方式提交,URL中包含查询参数, 格式：http://www.g.cn?search=p&name=s.....
	 *
	 * @param url 提交地址
	 * @return 响应消息
	 */
	public static String get(String url) {
		return get(url, null, null);
	}
	/**
	 * Get方式提交,URL中不包含查询参数, 格式：http://www.g.cn
	 *
	 * @param url  提交地址
	 * @param params 查询参数集, 键/值对
	 * @return 响应消息
	 */
	public static String get(String url, Map<String, String> params) {
		return get(url, params, null);
	}
	/**
	 * Get方式提交,URL中不包含查询参数, 格式：http://www.g.cn
	 *
	 * @param url 提交地址
	 * @param params 查询参数集, 键/值对
	 * @param charset 参数提交编码集
	 * @return 响应消息
	 */
	public static String get(String url, Map<String, String> params, String charset) {
		if (url == null || StringUtils.isEmpty(url)) {
			return null;
		}
		List<NameValuePair> qparams = getParamsList(params);
		if (qparams != null && qparams.size() > 0) {
			charset = (charset == null ? CHARSET_UTF8 : charset);
			String formatParams = URLEncodedUtils.format(qparams, charset);
			url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams) : (url
					.substring(0, url.indexOf("?") + 1) + formatParams);
		}
		CloseableHttpClient httpclient = getHttpClient(charset);
		HttpGet hg = new HttpGet(url);
		// 发送请求，得到响应
		String responseStr = null;
		try {
			responseStr = httpclient.execute(hg, responseHandler);
		} catch (ClientProtocolException e) {
			throw new RuntimeException("客户端连接协议错误", e);
		} catch (IOException e) {
			throw new RuntimeException("IO操作异常", e);
		} finally {
			abortConnection(hg, httpclient);
		}
		return responseStr;
	}
	/**
	 * Post方式提交,URL中不包含提交参数, 格式：http://www.g.cn
	 *
	 * @param url  提交地址
	 * @param params 提交参数集, 键/值对
	 * @return 响应消息
	 */
	public static String post(String url, Map<String, String> params) {
		return post(url, params, HttpClientUtil.CHARSET_UTF8);
	}
	/**
	 * Post方式提交,URL中不包含提交参数, 格式：http://www.g.cn
	 *
	 * @param url 提交地址
	 * @param params 提交参数集, 键/值对
	 * @param charset 参数提交编码集
	 * @return 响应消息
	 */
	public static String post(String url, Map<String, String> params, String charset) {
		if (url == null || StringUtils.isEmpty(url)) {
			return null;
		}
		// 创建HttpClient实例
		CloseableHttpClient httpclient = getHttpClient(charset);
		UrlEncodedFormEntity formEntity = null;
		try {
			if (charset == null || StringUtils.isEmpty(charset)) {
				formEntity = new UrlEncodedFormEntity(getParamsList(params));
			} else {
				formEntity = new UrlEncodedFormEntity(getParamsList(params), charset);
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("不支持的编码集", e);
		}
		HttpPost hp = new HttpPost(url);
		hp.setEntity(formEntity);
		// 发送请求，得到响应
		String responseStr = null;
		try {
			responseStr = httpclient.execute(hp, responseHandler);
		} catch (ClientProtocolException e) {
			throw new RuntimeException("客户端连接协议错误", e);
		} catch (IOException e) {
			throw new RuntimeException("IO操作异常", e);
		} finally {
			abortConnection(hp, httpclient);
		}		
		return responseStr;
	}
	/**
	 * Post方式提交,忽略URL中包含的参数,解决SSL双向数字证书认证
	 *
	 * @param url 提交地址
	 * @param params 提交参数集, 键/值对
	 * @param charset 参数编码集
	 * @param keystoreUrl 密钥存储库路径
	 * @param keystorePassword 密钥存储库访问密码
	 * @return 响应消息
	 * @throws RuntimeException
	 */
	public static String post(String url, Map<String, String> params, String charset, final URL keystoreUrl,
			final String keystorePassword) {
		if (url == null || StringUtils.isEmpty(url)) {
			return null;
		}
		UrlEncodedFormEntity formEntity = null;
		try {
			if (charset == null || StringUtils.isEmpty(charset)) {
				formEntity = new UrlEncodedFormEntity(getParamsList(params));
			} else {
				formEntity = new UrlEncodedFormEntity(getParamsList(params), charset);
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("不支持的编码集", e);
		}
		
		CloseableHttpClient httpclient = null;
		HttpPost hp = null;
		String responseStr = null;
		try {
			KeyStore keyStore = createKeyStore(keystoreUrl, keystorePassword);
//			KeyStore trustStore = createKeyStore(truststoreUrl, keystorePassword);

			HttpClientBuilder builder  = getHttpClientBuilder(charset);
			TrustStrategy trustStrategy = new TrustStrategy() {
				
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
					return true;
				}
			};
			
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(keyStore, trustStrategy).build();
			LayeredConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext);
			
			builder.setSSLSocketFactory(sslSocketFactory);

			httpclient = builder.build();
			
				
//			SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore,	keystorePassword, trustStore);
//			Scheme scheme = new Scheme(SSL_DEFAULT_SCHEME, socketFactory, SSL_DEFAULT_PORT);
//			httpclient.getConnectionManager().getSchemeRegistry().register(scheme);
			
			hp = new HttpPost(url);
			hp.setEntity(formEntity);
			responseStr = httpclient.execute(hp, responseHandler);			
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("指定的加密算法不可用", e);
		} catch (KeyStoreException e) {
			throw new RuntimeException("keytore解析异常", e);
		} catch (CertificateException e) {
			throw new RuntimeException("信任证书过期或解析异常", e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("keystore文件不存在", e);
		} catch (IOException e) {
			throw new RuntimeException("I/O操作失败或中断 ", e);
		} catch (KeyManagementException e) {
			throw new RuntimeException("处理密钥管理的操作异常", e);
		} finally {
			abortConnection(hp, httpclient);
		}
		return responseStr;
	}
	
	
	public static  HttpClientConnectionManager getConnectionManager(){
		
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(100);//连接池最大并发连接数
		connManager.setDefaultMaxPerRoute(100);//单路由最大并发数

		return connManager;
		
	}
	
	
	public static HttpClientBuilder getHttpClientBuilder(final String charset){
		
		HttpClientBuilder builder =  HttpClients.custom();
		
		Charset chartset = charset == null ? Charset.forName(CHARSET_UTF8) : Charset.forName(charset);
		ConnectionConfig.Builder connBuilder= ConnectionConfig.custom().setCharset(chartset);
		
		RequestConfig.Builder reqBuilder = RequestConfig.custom();
		reqBuilder.setExpectContinueEnabled(false);
		reqBuilder.setSocketTimeout(10 * 60 * 1000);
		reqBuilder.setConnectTimeout(10 * 60 * 1000);
		
		ServiceUnavailableRetryStrategy serviceUnavailableRetryStrategy = new DefaultServiceUnavailableRetryStrategy(3, 3000);
		builder.setServiceUnavailableRetryStrategy(serviceUnavailableRetryStrategy);
		//模拟浏览器，解决一些服务器程序只允许浏览器访问的问题
		builder.setUserAgent("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1)");
		
		builder.setDefaultRequestConfig(reqBuilder.build());
		builder.setDefaultConnectionConfig(connBuilder.build());
		builder.setConnectionManager(getConnectionManager());
		
		return builder;
		
	}
	
	/**
	 * 获取HttpClient实例
	 *
	 * @param charset 参数编码集, 可空
	 * @return HttpClient 对象
	 */
	public static CloseableHttpClient getHttpClient(final String charset){
		HttpClientBuilder builder = getHttpClientBuilder(charset);
		CloseableHttpClient httpclient = builder.build();
		
		return httpclient;
	}
	
	/**
	 * 释放HttpClient连接
	 * @param hrb 请求对象
	 * @param CloseableHttpClient 对象
	 */
	private static void abortConnection(final HttpUriRequest hrb, final CloseableHttpClient httpclient){
		if (hrb != null) {
			hrb.abort();
		}
		if (httpclient != null) {
			//httpclient.getConnectionManager().shutdown();
			try {
				logger.info("closing httpclient ...");
				httpclient.close();
			} catch (IOException e) {
				logger.error("failed to close httpclient",e);
			}
	
		}
	}
	
	/**
	 * 从给定的路径中加载此 KeyStore
	 *
	 * @param url keystore URL路径
	 * @param password keystore访问密钥
	 * @return keystore 对象
	 */
	private static KeyStore createKeyStore(final URL url, final String password)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		if (url == null) {
			throw new IllegalArgumentException("Keystore url may not be null");
		}
		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		InputStream is = null;
		try {
			is = url.openStream();
			keystore.load(is, password != null ? password.toCharArray() : null);
		} finally {
			if (is != null){
				is.close();
				is = null;
			}
		}
		return keystore;
	}
	
	/**
	 * 将传入的键/值对参数转换为NameValuePair参数集
	 *
	 * @param paramsMap 参数集, 键/值对
	 * @return NameValuePair参数集
	 */
	private static List<NameValuePair> getParamsList(Map<String, String> paramsMap) {
		if (paramsMap == null || paramsMap.size() == 0) {
			return null;
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> map : paramsMap.entrySet()) {
			params.add(new BasicNameValuePair(map.getKey(), map.getValue()));
		}
		return params;
	}
}