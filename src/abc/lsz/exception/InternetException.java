package abc.lsz.exception;

/**
 * 网络访问异常
 * @author BD
 *
 */
public class InternetException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InternetException(String msg){
		super(msg);
	}
}
