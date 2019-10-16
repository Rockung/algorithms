package org.example.hash;

/**
 * Principles for hashing
 *   1. determinism: mapped to the same address
 *   2. efficiency: expected O(1)
 *   3. surjection: cover the hashing space
 *   4. uniformity: avoid clustering
 *
 * Functions for hashing
 *   1. divide and residual
 *      hash(key) = key % M
 *      gcd(key, M)=1, M is a prime
 *      fixed point: hash(0)==0
 *      uniformity: level 0
 *   2. MAD
 *      multiply-add-divide
 *      hash(key) = [(a*key) + b] % M
 *      M is a prime, a > 0, b > 0, a % M != 0
 *   3. selecting digits
 *   4. mid-square
 *   5. folding
 *   6. XOR
 *   7. polynomial
 *      hash(s=x0x1..xn) = ...
 *
 * Collision Solutions
 *   1. multiple slots
 *   2. separate chaining
 *   3. closed hashing
 *      3.1 linear probing
 *      3.2 quadratic probing
 *      3.3 double quadratic probing
 *
 * See https://www.bilibili.com/video/av47473308/?p=11
 *     https://dsa.cs.tsinghua.edu.cn/~deng/ds/dsacpp/
 *     bucket array
 *     collision
 *     locality-sensitive hashing
 *     closed addressing
 *     probing sequence/chain
 *     lazy removal
 */
public class Hash {
}
