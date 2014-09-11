package test.base;



public class TestThread extends BaseTest implements Runnable {

	private int index;
	
	
	
	public int getIndex() {
		return index;
	}



	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public void run() {
		log.info("TestThread-" + Thread.currentThread().getId() + ":  index: " +index);

	}

}
