
public class Foo {
	public static void main(String[] args) {
		Integer a =13344;
		Integer b=13344;
		System.out.println(a==b);
		System.out.println(a.equals(b));
		
		String str="q;b;";
		String[] strs=str.split(";");
		System.out.println(strs.length);
		for (int i = 0; i < strs.length; i++) {
			System.out.println("strs[i]:"+strs[i]);
		}
	}
}
