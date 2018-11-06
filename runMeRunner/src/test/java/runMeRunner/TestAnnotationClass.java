package runMeRunner;

public class TestAnnotationClass {

	@RunMe
	public void test1(){
		
	}
	
	@RunMe
	public boolean test2() {
		return true;
	}
	
	public int test3() {
		return 4/0;
	}
}
