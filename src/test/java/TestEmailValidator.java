import junit.framework.Assert;

import com.example.tutorial.util.EmailValidator;
 
public class TestEmailValidator {
 
	@org.testng.annotations.Test
	public void test() {
		Assert.assertEquals(new EmailValidator().validate("dusan.stanojevic.cs@gmail.com"), true);
		Assert.assertEquals(new EmailValidator().validate("Dusan@"), false);
		Assert.assertEquals(new EmailValidator().validate("Dusan.com"), false);
		Assert.assertEquals(new EmailValidator().validate("@.com"), false);
	}
}