package com.company;

import java.io.*;

public class AlGem {

    //Демонстрация работы
    public static void main(String[] args)
    {
        int n=10000;
        double[] a=new double[n],b=new double[n],c=new double[n],f=new double[n];
        for(int i=0;i<n;i++)
        {
            a[i]= Math.round(Math.random()*1000);
            c[i]=a[i];
            b[i]=a[i]+a[i]+a[i]+Math.round(1000*Math.random());
        }
        f[0]=b[0]+c[0];
        for(int i=1;i<n-1;i++)
            f[i]=a[i]+b[i]+c[i];
        f[n-1]=a[n-1]+b[n-1];
        try
        {
            long buf = System.nanoTime();
            for (int i = 0; i < 10000; i++)
                Progon(a, b, c, f, n);
            System.out.println((double)(System.nanoTime()-buf)/Math.pow(10,13));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /*Метод прогонки для решения системы уравнений с трёхдиагональной матрицей.
     *(Работает только для матриц с диагональным преобладанием)
     *На вход подаются массивы содержащие элементы диагоналей и массив содержащий вектор коэффицентов правой части уравнений.
     *При вводе требуется доопределить первый элемент нижней диагонали и последний элемент верхней диагонали любыми числами.
     *Результатом работы является вектор решений системы уравнений*/
    public static double[] Progon(double[] a, double[] b, double[] c, double[] f,int n) throws Exception
    {
        double[] B=b.clone(),F=f.clone();
        if (Math.abs(b[0]) < Math.abs(c[0]))
            throw new IllegalArgumentException("Отсутствует диагональное преобладание");
        for(int i=1;i<n;i++)
        {
            if (Math.abs(b[i]) < Math.abs(c[i]) + Math.abs(a[i]))
                throw new IllegalArgumentException("Отсутствует диагональное преобладание");
            B[i] -= c[i - 1] * a[i] / B[i - 1];
            F[i]-=F[i-1]*a[i] / B[i - 1];
        }
        F[n-1]/=B[n-1];
        for(int i=n-2;i>=0;i--)
            F[i]=(F[i]-c[i]*F[i+1])/B[i];
        return F;
    }

    public static double RandError(int n)
    {
        double[] a=new double[n],b=new double[n],c=new double[n],f=new double[n];
        for(int i=0;i<n;i++)
        {
            a[i]= Math.random();
            c[i]=a[i];
            b[i]=2+a[i];
        }
        f[0]=b[0]+c[0];
        for(int i=1;i<n-1;i++)
            f[i]=a[i]+b[i]+c[i];
        f[n-1]=a[n-1]+b[n-1];
        try
        {
            double[] x=Progon(a,b,c,f,n);
            double error=0;
            for(double buf:x)
                error+=(buf-1)*(buf-1);
            return error;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }


    /*Метод решения Краевых задач для обыкновенных дифференциальных уравнений конечно-разностной схемой.
     *На вход подаются координаты концов отрезка, количество узлов, функции q и f, значения искомой функции на краях отрезка
     *Результатом работы является массив(размера n) приближенных значений функции в узлах*/
    public static double[] Raznostn(double st, double end, int n, delegetable q, delegetable F, double ua, double ub) throws Exception
    {
        if (st == end || n == 0)
            throw new Exception("Некорректный ввод");
        double h = (end - st) / n, buf = st;
        n--;
        double[] a=new double[n],b=new double[n],c=new double[n],f=new double[n];
        for(int i=0;i<n;i++)
        {
            buf+=h;
            a[i]=-1;
            b[i]=2+h*h*q.fun(buf);
            c[i]=-1;
            f[i]=h*h*F.fun(buf);
        }
        f[0]+=ua;
        f[n-1]+=ub;
        return Progon(a,b,c,f,n);
    }

    public static double Error(double[] y, delegetable u, double st, double end) throws Exception
    {
        if (st == end || y.length == 0)
            throw new Exception("Некорректный ввод");
        int n = y.length;
        double max = 0, h = (end - st) / (n + 1), buf = st;
        n--;
        for(int i=0;i<n;i++)
        {
            buf+=h;
            if(Math.abs(y[i]-u.fun(buf))>max)
                max=Math.abs(y[i]-u.fun(buf));
        }
        return max;
    }

    public static double[] Yakobi(double[][] a,double[] b,double e)
    {
        int n=a.length;
        double[][] B = new double[n][];
        double[] x=new double[n],buf=new double[n],Buf;
        for(int i=0;i<n;i++)
        {
            B[i]=new double[n];
            for(int j=0;j<n;j++)
                if(i!=j)
                    B[i][j]=-a[i][j]/a[i][i];
        }
        double q=Determinant(B);
        if(q>=1)
            return  null;
        boolean flag=true;
        while(flag)
        {
            flag=false;
            for(int i=0;i<n;i++)
            {
                buf[i]=b[i]/a[i][i];
                for(int j=0;j<n;j++)
                    buf[i]-=x[j]*B[i][j];
                if(Math.abs(x[i]-buf[i])>(1-q)*e/q)
                    flag=true;
            }
            Buf=x;
            x=buf;
            buf=Buf;
        }
        return x;
    }

    private static double Determinant(double[][] A)
    {
        int n=A.length;
        double[] buf;
        double det = 1;
        int max;
        for(int i=0;i<n;i++)
        {
            max=i;
            for(int j=i;j<n;j++)
            {
                if(A[j][i]>A[max][i])
                    max=j;
            }
            if(A[max][i]==0)
                return 0;
            buf=A[max];
            A[max]=A[i];
            A[i]=buf;
            det*=A[i][i];
            for(int j=n-1;j>=i;j--)
                A[i][j]/=A[i][i];
            for(int j=i+1;j<n;j++)
                for (int k = n - 1; k >= i; k--)
                    A[j][k] -= A[i][k] * A[j][i];
        }
        return det;
    }

    interface delegetable
    {
        double fun(double x);
    }
}