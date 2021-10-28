package com.co.pa;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> map = new HashMap<>();
        int numCases = scanner.nextInt();
        int num = 0;
        int[] arr0 = new int[numCases];
        int[] arr1 = new int[numCases];
        int[] fullArray = new int[numCases * 2];

        for (int i = 0; i < numCases; i++){
            String value1 = scanner.next();
            String value2 = scanner.next();

            if(!map.containsKey(value1)){
                num++;
                map.put(value1, num);
            }

            if(!map.containsKey(value2)){
                num++;
                map.put(value2, num);
            }

            arr0[i] = map.get(value1);
            arr1[i] = map.get(value2);
        }

        for(int i = 0; i < numCases; i++)
            fullArray[arr0[i]] = arr1[i];

        UnionFind unionFind = new UnionFind(fullArray.length);
        for(int i =1; i < fullArray.length; i++)
            if(fullArray[i] != 0)
                unionFind.union(i, fullArray[i]);

        System.out.println(unionFind.calculateSegments());
        Map<Integer, Integer> result = unionFind.getPairs();

        for (Map.Entry<Integer, Integer> index : result.entrySet()) {
            String first = "";
            String second = "";
            for (Map.Entry<String, Integer> jndex : map.entrySet()) {

                if (index.getKey().equals(jndex.getValue()))
                    first = jndex.getKey() + " ";

                if (index.getValue().equals(jndex.getValue()))
                    second = jndex.getKey();

            }

            System.out.println(first + second);
        }

    }
}


class UnionFind {
    private int size;
    private int[] control;

    public UnionFind(int size){
        this.size = size;
        this.control = new int[size + 1];

        for(int i = 0; i <= size; i++)
            this.control[i] = -1;
    }

    public int find(int node){
        if (this.control[node] <= -1) return node;
        return find(this.control[node]);
    }

    public boolean union(int node1, int node2){
        int parent1 = find(node1);
        int parent2 = find(node2);

        if(parent1 != parent2){
            if(node1 < node2){
                int value = this.control[parent1];
                this.control[parent1] = value - 1;
                this.control[node2] = node1;
            } else {
                int value = this.control[parent2];
                this.control[parent2] = value - 1;
                this.control[node2] = node1;
            }
        }
        return true;
    }

    public int calculateSegments(){
        int sum = 0;
        for (int i = 0; i < this.control.length; i++)
            if(this.control[i] < -1) sum++;

        return sum;
    }

    public Map<Integer, Integer> getPairs(){
        Map<Integer, Integer> result = new HashMap<>();
        int maxSegments = calculateSegments();
        int count = 0;
        for (int i = this.control.length - 1; i > 0 ; i--)
            if (this.control[i] > 0 && count < maxSegments) {
                int two = i;
                int one = find(i);
                result.put(one, two);
                count++;
            }

        return result;
    }
}
