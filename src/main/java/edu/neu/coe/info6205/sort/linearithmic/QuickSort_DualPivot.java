package edu.neu.coe.info6205.sort.linearithmic;

import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.util.Config;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;

public class QuickSort_DualPivot<X extends Comparable<X>> extends QuickSort<X> {

    public static final String DESCRIPTION = "QuickSort dual pivot";
    //public static Collator collatorObject;

    public QuickSort_DualPivot(String description, int N, Config config) {
        super(description, N, config);
        setPartitioner(createPartitioner());
    }

    /**
     * Constructor for QuickSort_3way
     * @param helper an explicit instance of Helper to be used.
     */
    public QuickSort_DualPivot(Helper<X> helper) {
        super(helper);
        setPartitioner(createPartitioner());
    }

    /**
     * Constructor for QuickSort_3way
     * @param N      the number elements we expect to sort.
     * @param config the configuration.
     */
    public QuickSort_DualPivot(int N, Config config) {
        this(DESCRIPTION, N, config);
    }

    @Override
    public Partitioner<X> createPartitioner()
    {
        return new Partitioner_DualPivot(getHelper());
    }

    public class Partitioner_DualPivot implements Partitioner<X> {

        public Partitioner_DualPivot(Helper<X> helper)
        {
            this.helper = helper;
        }

        /**
         * Method to partition the given partition into smaller partitions.
         * @param partition the partition to divide up.
         * @return an array of partitions, whose length depends on the sorting method being used.
         */
        public List<Partition<X>> partition(Partition<X> partition) {
            final X[] xs = partition.xs;
            final int lo = partition.from;
            final int hi = partition.to - 1;
            helper.swapConditional(xs, lo, hi);
            int lt = lo + 1;
            int gt = hi - 1;
            int i = lt;

            if (helper.instrumented()) {
            while (i <= gt) {
                if (collatorObject.compare(xs[i], xs[lo]) < 0)
                    helper.swap(xs, lt++, i++);
                else if (collatorObject.compare(xs[i], xs[hi]) > 0)
                    helper.swap(xs, i, gt--);
                else
                    i++;
            }
            helper.swap(xs, lo, --lt);
            helper.swap(xs, hi, ++gt);

            } else {
                while (i <= gt) {
                    if (collatorObject.compare(xs[i], xs[lo]) < 0)
                        helper.swap(xs, lt++, i++);
                    else if (collatorObject.compare(xs[i], xs[hi]) > 0)
                        helper.swap(xs, i, gt--);
                    else
                        i++;
                }
                swap(xs, lo, --lt);
                swap(xs, hi, ++gt);
            }
            List<Partition<X>> partitions = new ArrayList<>();
            partitions.add(new Partition<>(xs, lo, lt));
            partitions.add(new Partition<>(xs, lt + 1, gt));
            partitions.add(new Partition<>(xs, gt + 1, hi + 1));
            return partitions;
        }

        // CONSIDER invoke swap in BaseHelper.
        private void swap(X[] ys, int i, int j) {
            X temp = ys[i];
            ys[i] = ys[j];
            ys[j] = temp;
        }

        private final Helper<X> helper;

    }
}

