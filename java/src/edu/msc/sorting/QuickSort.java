package edu.msc.sorting;

/**
 * Created by mcq1 on 3/24/2016.
 */
public class QuickSort {
    static void quicksort(int a[], int p, int r)
    {
        if(p < r)
        {
            int q;
            q = partition(a, p, r);
            quicksort(a, p, q);
            quicksort(a, q+1, r);
        }
    }

    static int partition(int a[], int p, int r)
    {
        int i, j, pivot, temp;
        pivot = a[p];
        i = p;
        j = r;
        while(true)
        {
            while(a[i] < pivot && a[i] != pivot)
                i++;
            while(a[j] > pivot && a[j] != pivot)
                j--;
            if(i < j)
            {
                temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
            else
            {
                return j;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = new int [] {5,34,25,4,6,8};
        QuickSort.quicksort(a, 0, a.length-1);
    }

}
