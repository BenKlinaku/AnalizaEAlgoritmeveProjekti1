import java.util.Random;

public class Solovay_Strassen{
    static long modulo(long base, long exponent, long mod)
    {
        long x = 1;
        long y = base;
        while (exponent > 0)
        {
            if (exponent % 2 == 1)
                x = (x * y) % mod;

            y = (y * y) % mod;
            exponent = exponent / 2;
        }
        return x % mod;
    }
    static long calculateJacobian(long a, long n)
    {
        if (n <= 0 || n % 2 == 0)
            return 0;
        long ans = 1L;
        if (a < 0)
        {
            a = -a; // (a/n) = (-a/n)*(-1/n)
            if (n % 4 == 3)
                ans = -ans; // (-1/n) = -1 if n = 3 (mod 4)
        }
        if (a == 1)
            return ans; // (1/n) = 1
        while (a != 0)
        {
            if (a < 0)
            {
                a = -a; // (a/n) = (-a/n)*(-1/n)
                if (n % 4 == 3)
                    ans = -ans; // (-1/n) = -1 if n = 3 (mod 4)
            }
            while (a % 2 == 0)
            {
                a /= 2;
                if (n % 8 == 3 || n % 8 == 5)
                    ans = -ans;
            }
            long temp = a;
            a = n;
            n = temp;

            if (a % 4 == 3 && n % 4 == 3)
                ans = -ans;
            a %= n;
            if (a > n / 2)
                a = a - n;
        }
        if (n == 1)
            return ans;
        return 0;
    }
    static boolean solovoyStrassen(long p, int iteration)
    {
        if (p < 2)
            return false;
        if (p != 2 && p % 2 == 0)
            return false;
        Random rand = new Random();
        for(int i = 0; i < iteration; i++)
        {
            long r = Math.abs(rand.nextLong());
            long a = r % (p - 1) + 1;
            long jacobian = (p + calculateJacobian(a, p)) % p;
            long mod = modulo(a, (p - 1) / 2, p);

            if (jacobian == 0 || mod != jacobian)
                return false;
        }
        return true;
    }

    public static void main (String[] args)
    {
        int iter = 50;
        long number = 251002613;

        System.out.println("Solovay-Strassen Primality Algorithm");
        if (solovoyStrassen(number, iter))
            System.out.println(number + " is prime");
        else
            System.out.println(number + " is composite");
    }
}