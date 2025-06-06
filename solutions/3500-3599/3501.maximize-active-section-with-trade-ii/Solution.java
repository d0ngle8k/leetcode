class Solution {
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int activeCount = 0;
        for (char ch : s.toCharArray())
            if (ch == '1')
                activeCount++;

        List<int[]> segments = new ArrayList<>();
        for (int i = 0, start = 0; i < n; i++) {
            if (i == n - 1 || s.charAt(i) != s.charAt(i + 1)) {
                segments.add(new int[]{start, i - start + 1});
                start = i + 1;
            }
        }
        int segmentCount = segments.size();

        int maxPower = 20, INF = (int) 1e9;
        int NEG_INF = -INF;
        int[][] rmq = new int[maxPower][segmentCount];
        for (int i = 0; i < maxPower; i++) {
            Arrays.fill(rmq[i], NEG_INF);
        }

        for (int i = 0; i < segmentCount; i++) {
            if (s.charAt(segments.get(i)[0]) == '0' && i + 2 < segmentCount) {
                rmq[0][i] = segments.get(i)[1] + segments.get(i + 2)[1];
            }
        }

        for (int power = 1, rangeLen = 2; power < maxPower; power++, rangeLen *= 2) {
            for (int i = 0, j = rangeLen - 1; j < segmentCount; i++, j++) {
                rmq[power][i] = Math.max(rmq[power - 1][i],
                                           rmq[power - 1][i + rangeLen / 2]);
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int[] query : queries) {
            int left = query[0], right = query[1];
            int leftIndex = binarySearch(segments, left) - 1;
            int rightIndex = binarySearch(segments, right) - 1;

            if (rightIndex - leftIndex + 1 <= 2) {
                result.add(activeCount);
                continue;
            }

            int bestIncrease = Math.max(getMaxInRange(rmq, leftIndex + 1, rightIndex - 3, NEG_INF), 0);
            bestIncrease = Math.max(bestIncrease, calculateNewSections(s, segments, left, right, leftIndex, rightIndex, leftIndex, NEG_INF));
            bestIncrease = Math.max(bestIncrease, calculateNewSections(s, segments, left, right, leftIndex, rightIndex, rightIndex - 2, NEG_INF));

            result.add(activeCount + bestIncrease);
        }
        return result;
    }

    private int binarySearch(List<int[]> segments, int key) {
        int lo = 0, hi = segments.size();
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (segments.get(mid)[0] > key)
                hi = mid;
            else
                lo = mid + 1;
        }
        return lo;
    }

    private int getMaxInRange(int[][] rmq, int left, int right, int NEG_INF) {
        if (left > right)
            return NEG_INF;
        int power = 31 - Integer.numberOfLeadingZeros(right - left + 1);
        return Math.max(rmq[power][left], rmq[power][right - (1 << power) + 1]);
    }

    private int getSegmentSize(String s, List<int[]> segments, int left, int right, int leftIndex, int rightIndex, int i) {
        if (i == leftIndex)
            return segments.get(leftIndex)[1] - (left - segments.get(leftIndex)[0]);
        if (i == rightIndex)
            return right - segments.get(rightIndex)[0] + 1;
        return segments.get(i)[1];
    }

    private int calculateNewSections(String s, List<int[]> segments, int left, int right, int leftIndex, int rightIndex, int i, int NEG_INF) {
        if (i < 0 || i + 2 >= segments.size() || s.charAt(segments.get(i)[0]) == '1')
            return NEG_INF;
        int size1 = getSegmentSize(s, segments, left, right, leftIndex, rightIndex, i);
        int size2 = getSegmentSize(s, segments, left, right, leftIndex, rightIndex, i + 2);
        return size1 + size2;
    }
}
