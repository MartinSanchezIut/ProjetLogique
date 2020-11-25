package projetlogique.utils;

public class Couple<T> {


    private T first;
    private T second;


    public Couple() {}

    public Couple(T s1, T s2) {
        this.first = s1;
        this.second = s2;
    }
    
    
    
    public T getFirst() {
		return first;
	}
    
    public T getSecond() {
		return second;
	}
    
    public void setFirst(T first) {
		this.first = first;
	}
    
    public void setSecond(T second) {
		this.second = second;
	}
}
