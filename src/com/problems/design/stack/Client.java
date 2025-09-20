package com.problems.design.stack;

import java.util.ArrayList;

class Stack <T> {
    ArrayList<T> elements;
    int top;

    public Stack(){
        this.elements = new ArrayList<>();
        this.top = -1;
    }

    /* push, pop, peak */
    public void push(T data){
        elements.add(data);
        top++;
    }

    public T pop(){
        T data = elements.remove(top);
        top--;
        return data;
    }

    public T peak(){
        return elements.get(top);
    }

    public boolean isEmpty(){
        return elements.isEmpty();
    }

}

public class Client {
    public static void main(String[] args) {
        Stack<Integer> st = new Stack<>();
        st.push(2);
        st.push(3);
        st.push(4);
        st.push(5);
        st.pop();
        st.push(7);
        System.out.println(st.peak());

    }
}
