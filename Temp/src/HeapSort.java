import java.util.*;

public class HeapSort
{
    public static void main(String[] args)
    {
        System.out.println(Arrays.toString(HeapSort(new double[]{5,7,1,5,9,2,8,4,0},9)));
        System.out.println(Arrays.toString(HeapForm(new double[]{1,2,3,4,5,6,7,8,9},0,9)));
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

    public static double[] HeapSort(double[] arg)
    {
        return HeapSort(arg.clone(), arg.length);
    }
    private static double[] HeapSort(double[] arg,int end)
    {
        if(end<2)
            return arg;
        double buf;
        for(int i=end/2-1;i>=0;i--)
            arg = HeapForm(arg, i, end);
        buf = arg[0];
        arg[0] = arg[end - 1];
        arg[end - 1] = buf;
        arg = HeapSort(arg, end - 1);
        return arg;
    }
}
