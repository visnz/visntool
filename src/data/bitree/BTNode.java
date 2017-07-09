package data.bitree;

import base.IO.log.Log;

/**
 * Created by zyvis on 2017/6/6.
 */
public class BTNode<T extends Comparable> {

    protected T element;

    public int getRepeatTime() {
        return repeatTime;
    }

    protected int repeatTime=0;
    protected BTNode left=null,right=null;

    public BTNode() {
    }

    public BTNode(T element) {
        this();
        this.element = element;
    }

    public BTNode(T element, BTNode left, BTNode right) {
        this(element);
        this.left = left;
        this.right = right;
    }
    public BTNode getLeft() {
        return left;
    }

    public BTNode getRight() {
        return right;
    }

    protected void replaceLeftToThis(){
        this.element= (T) this.left.element;
    }
    protected void replaceRightToThis(){
        this.element= (T) this.right.element;
    }
    protected BTNode newLeft(){
        if(left==null)
        left=new BTNode();
        return left;
    }
    protected BTNode newRight(){
        if(right==null)
        right=new BTNode();
        return right;
    }

    public T getElement() {
        return element;
    }

    /**
     * 输出函数
     */
    public void visit()
    {
        Log.d(this.element.toString());
    }

    public void setElement(T element) {
        this.element = element;
    }
}

