package test.zdw.service;


public class AppPageServiceProcesser {

    // class MethodCallback implements MethodInterceptor {
    //
    // @Override
    // public Object intercept(Object obj, Method method, Object[] args,
    // MethodProxy proxy)
    // throws Throwable {
    // return proxy.invokeSuper(obj, args);
    // }
    //
    // }
    //
    // public static String exeAnnotation(Class<?> entity) {
    // String result = "";
    //
    // AppPageServiceProcesser ap = new AppPageServiceProcesser();
    //
    // Method[] methods = entity.getMethods();
    //
    // for (Method m : methods) {
    // PageServiceMapping aps = m.getAnnotation(PageServiceMapping.class);
    // if (aps != null) {
    // String path = aps.value();
    //
    // System.out.println(path);
    //
    // System.out.println("getDeclaringClass: " + m.getDeclaringClass());
    //
    // System.out.println("getName: " + m.getName());
    // System.out.println("getReturnType: " + m.getReturnType());
    // System.out.println("getParameters: " + m.getParameters());
    //
    // Enhancer enhancer = new Enhancer();
    // enhancer.setSuperclass(m.getDeclaringClass());
    // enhancer.setCallback(ap.new MethodCallback());
    // final Object obj = enhancer.create();
    //
    // System.out.println("obj: " + obj);
    //
    // PageService ps = new PageService() {
    //
    // @Override
    // public String handleRequest(ParameterMapper paramMapper,
    // Map<String, Object> view) {
    // String pagePath = "";
    // // try {
    // // pagePath = (String) m.invoke(obj, paramMapper, view);
    // // } catch (Exception e) {
    // // // TODO Auto-generated catch block
    // // e.printStackTrace();
    // // }
    //
    // return pagePath;
    // }
    // };
    //
    // }
    // }
    //
    // return result;
    // }
    //
    // public static void main(String[] args) {
    //
    // String a = exeAnnotation(ZdwTest2PageService.class);
    // }
}
