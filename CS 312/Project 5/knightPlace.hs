knightPlace x = reverse(solve (length x - 1))
    where
    solve 0 = solve3 0 
    solve k = solve3 k ++ (solve (k-1))
    solve3 k = [zero q++[] | let q = reverse (solve4 (length x) k)]
    solve4 0 k = []
    solve4 j k
        | not (safe j k) = solve4 (j-1) k
        | (safe j k) = j:solve4 (j-1) k
    safe j k = and [not (checks j k a) | a <- [0..(length x - 1)]]
    checks j k a = j == x!!a || x!!k /= 0|| (abs(j - x!!a) == abs(k-a) && x!!a /= 0) || (abs(j - x!!a) + abs(k - a) == 3 && x!!a /= 0)
    zero q
        |null q == True = [0]
        |null q == False = q