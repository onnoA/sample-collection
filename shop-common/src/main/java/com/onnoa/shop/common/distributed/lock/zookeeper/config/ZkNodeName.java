package com.onnoa.shop.common.distributed.lock.zookeeper.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @Description: zk的顺序节点对象封装
 * @Author: onnoA
 * @Date: 2020/6/14 16:50
 */
class ZkNodeName implements Comparable<ZkNodeName> {
    private static final Logger LOG = LoggerFactory.getLogger(ZkNodeName.class);

    public static final char splitChar = '-';
    private final String nodePath;
    private String prefix;
    private int sequence = -1;


    /***
     * 构造节点
     * @param nodePath 节点全路径
     */
    public ZkNodeName(String nodePath) {
        if (nodePath == null) {
            throw new NullPointerException("id cannot be null");
        }
        this.nodePath = nodePath;
        this.prefix = nodePath;
        int idx = nodePath.lastIndexOf(splitChar);
        if (idx >= 0) {
            this.prefix = nodePath.substring(0, idx);
            try {
                this.sequence = Integer.parseInt(nodePath.substring(idx + 1));
            } catch (NumberFormatException e) {
                LOG.info("Number format exception for " + idx, e);
            } catch (ArrayIndexOutOfBoundsException e) {
                LOG.info("Array out of bounds for " + idx, e);
            }
        }
    }

    /***
     * 节点路径
     * @return
     */
    public String getNodePath() {
        return nodePath;
    }

    /***
     * 节点的顺序号
     * @return
     */
    public int getSequence() {
        return sequence;
    }

    /***
     * 节点前缀
     * @return
     */
    public String getPrefix() {
        return prefix;
    }


    @Override
    public String toString() {
        return nodePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZkNodeName sequence = (ZkNodeName) o;

        return nodePath.equals(sequence.nodePath);
    }

    @Override
    public int hashCode() {
        return nodePath.hashCode();
    }

    public int compareTo(ZkNodeName that) {
        int answer = this.prefix.compareTo(that.prefix);
        if(answer !=0 ) {
            return answer;
        }

        int this_s = this.sequence;
        int that_s = that.sequence;
        if (this_s == -1 || that_s == -1) {
            return this.nodePath.compareTo(that.nodePath);
        }
        return this_s - that_s;
    }

    public static void main(String[] args) {
        ZkNodeName z1 = new ZkNodeName("abdefg-0000066");
        ZkNodeName z2 = new ZkNodeName("abdefg-0000002");
        ZkNodeName z3 = new ZkNodeName("abdefg-0000039");
        ZkNodeName z4 = new ZkNodeName("/abc/ddd/abdefg-0000039");
        ZkNodeName z5 = new ZkNodeName("/abc/ddd/abdefg-0000034");
        System.out.println(z4.equals(z5));
        SortedSet<ZkNodeName> sortedSet = new TreeSet<>();
        sortedSet.add(z1);
        sortedSet.add(z2);
        sortedSet.add(z3);
        sortedSet.add(z4);
        sortedSet.add(z5);
        SortedSet<ZkNodeName> headSet = sortedSet.headSet(z3);
        for(ZkNodeName n : headSet) {
            System.out.println(n.getNodePath());
        }

    }
}
