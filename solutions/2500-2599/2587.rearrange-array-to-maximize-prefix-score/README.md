---
comments: true
difficulty: Medium
rating: 1336
source: Weekly Contest 336 Q2
tags:
    - Greedy
    - Array
    - Prefix Sum
    - Sorting
---

<!-- problem:start -->

# [2587. Rearrange Array to Maximize Prefix Score](https://leetcode.com/problems/rearrange-array-to-maximize-prefix-score)

## Description

<!-- description:start -->

<p>You are given a <strong>0-indexed</strong> integer array <code>nums</code>. You can rearrange the elements of <code>nums</code> to <strong>any order</strong> (including the given order).</p>

<p>Let <code>prefix</code> be the array containing the prefix sums of <code>nums</code> after rearranging it. In other words, <code>prefix[i]</code> is the sum of the elements from <code>0</code> to <code>i</code> in <code>nums</code> after rearranging it. The <strong>score</strong> of <code>nums</code> is the number of positive integers in the array <code>prefix</code>.</p>

<p>Return <em>the maximum score you can achieve</em>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<pre>
<strong>Input:</strong> nums = [2,-1,0,1,-3,3,-3]
<strong>Output:</strong> 6
<strong>Explanation:</strong> We can rearrange the array into nums = [2,3,1,-1,-3,0,-3].
prefix = [2,5,6,5,2,2,-1], so the score is 6.
It can be shown that 6 is the maximum score we can obtain.
</pre>

<p><strong class="example">Example 2:</strong></p>

<pre>
<strong>Input:</strong> nums = [-2,-3,0]
<strong>Output:</strong> 0
<strong>Explanation:</strong> Any rearrangement of the array will result in a score of 0.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>-10<sup>6</sup> &lt;= nums[i] &lt;= 10<sup>6</sup></code></li>
</ul>

<!-- description:end -->

## Solutions

<!-- solution:start -->

### Solution 1: Greedy + Sorting

To maximize the number of positive integers in the prefix sum array, we need to make the elements in the prefix sum array as large as possible, that is, to add as many positive integers as possible. Therefore, we can sort the array $nums$ in descending order, then traverse the array, maintaining the prefix sum $s$. If $s \leq 0$, it means that there can be no more positive integers in the current position and the positions after it, so we can directly return the current position.

Otherwise, after the traversal, we return the length of the array.

The time complexity is $O(n \times \log n)$, and the space complexity is $O(\log n)$. Here, $n$ is the length of the array $nums$.

<!-- tabs:start -->

#### Python3

```python
class Solution:
    def maxScore(self, nums: List[int]) -> int:
        nums.sort(reverse=True)
        s = 0
        for i, x in enumerate(nums):
            s += x
            if s <= 0:
                return i
        return len(nums)
```

#### Java

```java
class Solution {
    public int maxScore(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        long s = 0;
        for (int i = 0; i < n; ++i) {
            s += nums[n - i - 1];
            if (s <= 0) {
                return i;
            }
        }
        return n;
    }
}
```

#### C++

```cpp
class Solution {
public:
    int maxScore(vector<int>& nums) {
        sort(nums.rbegin(), nums.rend());
        long long s = 0;
        int n = nums.size();
        for (int i = 0; i < n; ++i) {
            s += nums[i];
            if (s <= 0) {
                return i;
            }
        }
        return n;
    }
};
```

#### Go

```go
func maxScore(nums []int) int {
	sort.Ints(nums)
	n := len(nums)
	s := 0
	for i := range nums {
		s += nums[n-i-1]
		if s <= 0 {
			return i
		}
	}
	return n
}
```

#### TypeScript

```ts
function maxScore(nums: number[]): number {
    nums.sort((a, b) => a - b);
    const n = nums.length;
    let s = 0;
    for (let i = 0; i < n; ++i) {
        s += nums[n - i - 1];
        if (s <= 0) {
            return i;
        }
    }
    return n;
}
```

#### Rust

```rust
impl Solution {
    pub fn max_score(mut nums: Vec<i32>) -> i32 {
        nums.sort_by(|a, b| b.cmp(a));
        let mut s: i64 = 0;
        for (i, &x) in nums.iter().enumerate() {
            s += x as i64;
            if s <= 0 {
                return i as i32;
            }
        }
        nums.len() as i32
    }
}
```

<!-- tabs:end -->

<!-- solution:end -->

<!-- problem:end -->
