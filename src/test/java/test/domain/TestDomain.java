package test.domain;

import java.util.Arrays;
import java.util.List;

import org.jiucai.appframework.base.domain.BaseDomain;

public class TestDomain extends BaseDomain {

	private static final long serialVersionUID = 7214714816908989510L;

	private Integer id;
	private String name;

	private List<String> n1;
	private String[] n2;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getN1() {
		return n1;
	}

	public void setN1(List<String> n1) {
		this.n1 = n1;
	}

	public String[] getN2() {
		return n2;
	}

	public void setN2(String[] n2) {
		this.n2 = n2;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TestDomain [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", n1=");
		builder.append(n1);
		builder.append(", n2=");
		builder.append(Arrays.toString(n2));
		builder.append("]");
		return builder.toString();
	}

}
