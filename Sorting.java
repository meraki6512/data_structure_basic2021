// 자료구조 Homework#4: Sorting - 소스코드
// 컴퓨터학부 20201482학번 은지우

import java.util.Random;

public class Sorting {

    private final int[] array;
    int[] tmp;
    Sorting(){
        array = new int[32]; //배열
        tmp = new int[32]; //for merge sort (정렬 중 배열을 임시로 저장)
    }

    // 랜덤 배열 (정수 32개)
    void randomArray(){
        for (int i=0; i<32; i++){
            Random r = new Random();
            array[i] = r.nextInt(32); //정수 32개 랜덤 선택(0-31)
            for(int j=0; j<i; j++){
                if(array[i] == array[j]){ //중복 제거
                    i--;
                }
            }
        }
    }

    // 배열 출력
    void printArray(){
        for (int i=0; i<32; i++){
            System.out.print(array[i]+" "); //배열 출력
        }
        System.out.println();
    }

    // main
    public static void main(String[] args) {
        System.out.println("-----Sorting Program-----");
        Sorting sorting = new Sorting();
        //select
        System.out.println("\n[ Select ]");
        sorting.randomArray(); //랜덤 배열 생성
        System.out.println("--Before--");
        sorting.printArray(); //정렬 전 배열 출력
        sorting.select(); // 정렬 (select)
        System.out.println("--After--");
        sorting.printArray(); //정렬 후 배열 출력
        //insert
        System.out.println("\n[ Insert ]");
        sorting.randomArray(); //랜덤 배열 생성
        System.out.println("--Before--");
        sorting.printArray(); //정렬 전 배열 출력
        sorting.insert(); // 정렬 (insert)
        System.out.println("--After--");
        sorting.printArray(); //정렬 후 배열 출력
        //merge
        System.out.println("\n[ Merge ]");
        sorting.randomArray(); //랜덤 배열 생성
        System.out.println("--Before--");
        sorting.printArray(); //정렬 전 배열 출력
        System.out.println("--Changing--");
        sorting.merge(0, 31); // 정렬 (merge)
        System.out.println("--After--");
        sorting.printArray(); //정렬 후 배열 출력
        //quick
        System.out.println("\n[ Quick ]");
        sorting.randomArray(); //랜덤 배열 생성
        System.out.println("--Before--");
        sorting.printArray(); //정렬 전 배열 출력
        System.out.println("--Changing--");
        sorting.quick(0, 31); // 정렬 (Quick)
        System.out.println("--After--");
        sorting.printArray(); //정렬 후 배열 출력
    }

    void quick (int first, int last) {
        if (first<last){
            //split
            int pivot = partition(first,last); //피벗(기준) (+sort)
            //recurse
            quick(first,pivot-1); //좌
            quick(pivot+1,last); //우
        }
    }

    int partition (int first, int last) {
        int pivotIndex = first;
        int lo = first;
        int hi = last;
        //pivot을 기준으로 lo, hi에 숫자를 배치
        while (lo<hi){ //lo와 hi가 만나기 전까지 up&down 반복
            while (array[lo]<array[pivotIndex]){
                lo++; //pivot보다 크거나 같은 숫자가 나올 때까지 lo up
            }
            while (array[hi]>array[pivotIndex]){
                hi--; //pivot보다 작거나 같은 숫자가 나올 때까지 hi down
            }
            if (lo<hi) { //교차X: swap lo with hi
                int temp = array[lo];
                array[lo] = array[hi];
                array[hi] = temp;
            }
        }
        //print (과정)
        for (int t=0; t<32; t++){
            System.out.print(array[t]+" "); //배열 출력
        }
        System.out.println();
        //pivot의 위치에 있는 lo index를 반환
        return lo;
    }

    void merge(int first, int last) {
        if (first<last) {
            //split
            int mid = (first+last)/2; //중간
            merge(first, mid); //좌
            merge(mid+1,last); //우
            //merge (+sort)
            int i = first, j = mid+1, index = i;
            while ((i<=mid)&&(j<=last)){ //좌우 배열 중 하나가 먼저 인덱스를 벗어나기 전까지
                if (array[i]<=array[j]){
                    tmp[index++] = array[i++];
                }
                else {
                    tmp[index++] = array[j++];
                }
            }
            if (i<=mid) { //좌측 배열에 숫자가 남아있을 경우
                for (int t=i; t<=mid; t++){
                    tmp[index++] = array[t];
                }
            }
            else { //우측 배열에 숫자가 남아있을 경우
                for (int t=j; t<=last; t++) {
                    tmp[index++] = array[t];
                }
            }
            //paste
            if (last + 1 - first >= 0) System.arraycopy(tmp, first, array, first, last + 1 - first);
            //print (과정)
            for (int t=0; t<32; t++){
                System.out.print(tmp[t]+" "); //배열 출력
            }
            System.out.println();
        }
    }

    void insert() {
        System.out.println("--Changing--");
        for (int i=0; i<32; i++){ //32개 반복
            //swap
            for (int j=0; j<i; j++){ // index:0 ~ index:i 까지 비교
                if (array[i]<array[j]){ // 내림차순인 경우, swap
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
            //print (과정)
            System.out.print("["+i+"]: ");
            printArray();
        }
    }

    void select() {
        System.out.println("--Changing--");
        for (int i=0; i<32; i++){ //32번 반복
            //min index 찾기
            int min_index = i; //min 인덱스를 i로 지정
            for(int j=31; j>i; j--){ //index:31부터 index:i까지 반복
                if (array[j] < array[min_index]){ //min보다 작은 값이 있다면
                    min_index = j; //min 인덱스를 해당 index로 변경
                }
            }
            //swap
            int temp = array[i];
            array[i] = array[min_index];
            array[min_index] = temp;
            //print (과정)
            System.out.print("["+i+"]: ");
            printArray();
        }
    }
}
