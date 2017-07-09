package data.bitree;

import base.IO.log.Log;

/**
 * 提供一个无法存储重复数据的二叉树
 * 在插入的时候即完成排序
 * 调用方法即直接创建插入调用即可
 * 大可不必访问Node进行筛选
 * <p>
 * Created by zyvis on 2017/6/6.
 */
public final class BiSearchTree<T extends Comparable> {
    private BTNode<T> root = null;

    /**
     * 先根
     *
     * @param t
     */
    private void preOrder(BTNode<T> t) {
        if (t != null) {
            t.visit();
            preOrder(t.getLeft());
            preOrder(t.getRight());
        }
    }

    /**
     * 中根
     *
     * @param t
     */
    private void inOrder(BTNode<T> t) {
        if (t != null) {
            inOrder(t.getLeft());
            t.visit();
            inOrder(t.getRight());
        }
    }

    /**
     * 后根输出
     *
     * @param t
     */
    private void postOrder(BTNode<T> t) {
        if (t != null) {
            postOrder(t.getLeft());
            postOrder(t.getRight());
            t.visit();
        }
    }

    private void myOrder(BTNode<T> t) {
        if (t != null) {
            myOrder(t.getRight());
            t.visit();
            myOrder(t.getLeft());
        }
    }
    public BTNode<T> getMostElement(){
        final BTNode<T> test=root;
        findMostEle(test,root);
        return test;
    }
    private void findMostEle(BTNode<T> tmp,BTNode<T> t) {
        if (t != null) {
            findMostEle(tmp,t.getRight());
            if(t.repeatTime>tmp.repeatTime)tmp=t;
            findMostEle(tmp,t.getLeft());
        }
    }

    private void insert(BTNode<T> ptr, T item) {
        //Log.d("insert item : "+item.toString());
        if (ptr.element == null) {
            ptr.element = item;
            Log.d("return");
            return;

        }
        int tmp=ptr.getElement().compareTo(item);
        Log.d("ptr.getElement().compareTo(item):" +tmp);
        if(tmp == 0)ptr.repeatTime++;
        else if (tmp == 1) insert(ptr.newLeft(), item);
        else if (tmp == -1) insert(ptr.newRight(), item);
    }


    private void delete(BTNode<T> ptr, T item) {
        if (ptr != null) {
            if (ptr.getElement().compareTo(item) == 1) delete(ptr.getLeft(), item);
            else if (ptr.getElement().compareTo(item) == -1) delete(ptr.getRight(), item);

            else if (ptr.getLeft() != null && ptr.getRight() != null) {
                BTNode<T> min;
                min = ptr.getRight();

                while (min.getLeft() != null) min = min.getLeft();

                ptr.setElement(min.getElement());

                delete(ptr.getRight(), min.getElement());
            } else {
                if (ptr.getLeft() == null) ptr.replaceRightToThis();
                else if (ptr.getRight() == null) ptr.replaceLeftToThis();
            }
        }
    }

    public BiSearchTree() {
        root = null;
    }

    /**
     * 先根输出，会进行遍历
     */
    public void preOrder() {
        preOrder(root);
    }

    /**
     * 中根输出
     * 根据原本排序
     * 这个方法是用于降序输出
     */
    public void inOrder() {
        Log.d("inorder run");
        inOrder(root);
    }

    /**
     * 后根
     */
    public void postOrder() {
        postOrder(root);
    }

    /**
     * 相对于Inorder的逆序数出
     */
    public void ninOrder() {
        myOrder(root);
    }


    /**
     * 获取树的根节点
     *
     * @return
     */
    public BTNode<T> getRoot() {
        return root;
    }

    /**
     * 获取某一节点的左节点
     *
     * @param current 节点
     * @return 节点的左节点
     */
    public BTNode<T> left(BTNode<T> current) {
        return root != null ? current.getLeft() : null;
    }

    /**
     * 获取某一节点的右节点
     *
     * @param current 节点
     * @return 节点的右节点
     */
    public BTNode<T> right(BTNode<T> current) {
        return root != null ? current.getRight() : null;
    }

    /**
     * 寻找某一树中的节点对象，可以用于对象反求节点，获取其他对象
     *
     * @param item 对象
     * @return 对象的节点
     */
    public BTNode<T> find(T item) throws NullPointerException {
        if (root != null) {
            BTNode<T> temp = root;
            while (temp != null) {
                if (temp.getElement().equals(item)) return temp;
                if (temp.getElement().compareTo(item) < 0) temp = temp.getRight();
                else temp = temp.getLeft();
            }
        }
        throw new NullPointerException();
    }

    /**
     * 将对象作为节点插入到树中
     * @param item 对象
     */
    public void insert(T item) {
        if (root == null)
            root = new BTNode<T>();
        insert(root, item);
    }

    /**
     * 在树中删除指定对象
     * @param item
     */
    public void delete(T item) {
        delete(getRoot(), item);
    }
}
