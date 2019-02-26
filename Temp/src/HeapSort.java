import java.util.*;

public class HeapSort
{
    public static void main(String[] args)
    {
        System.out.println(Arrays.toString(HeapSort(new double[]{5,7,1,5,9,2,8,4,0},9)));
    }
    private static double[] HeapForm(double[] arg,int i,int end)
    {
        double[] arr=arg.clone();
        double buf;
        if(i*2+2<end)
        {
            if (arr[i * 2 + 1] > arr[i * 2 + 2] && arr[i * 2 + 1] > arr[i])
            {
                buf = arr[i];
                arr[i] = arr[i * 2 + 1];
                arr[i * 2 + 1] = buf;
                arr=HeapForm(arr,i*2+1,end);
            } else if (arr[i * 2 + 2] > arr[i])
            {
                buf = arr[i];
                arr[i] = arr[i * 2 + 2];
                arr[i * 2 + 2] = buf;
                arr=HeapForm(arr,i*2+2,end);
            }
        }
        else if(i*2+1<end && arr[i*2+1]>arr[i])
        {
            buf=arr[i];
            arr[i]=arr[i+1];
            arr[i+1]=buf;
        }
        return arr;
    }
    private static double[] HeapSort(double[] arg,int end)
    {
        double[] arr;
        if(arg.length==end)
            arr=arg.clone();
        else
            arr=arg;
        if(end<2)
            return arr;
        double buf;
        for(int i=end/2-1;i>=0;i--)
            arr=HeapForm(arr,i,end);
        buf=arr[0];
        arr[0]=arr[end-1];
        arr[end-1]=buf;
        arr=HeapSort(arr,end-1);
        return arr;
    }
}
