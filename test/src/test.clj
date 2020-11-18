(ns test)

;(println (reduce + 10 [1 2 3]))
;
;
;(defn concat-to-list [x listt]
;  (map (fn [y] (str x y)) listt))
;
;(defn flattenn [listt]
;  (reduce concat listt))
;
;(defn remove-string [string listt]
;  (reduce (fn [xs z] (remove (fn [a] (= a (str z))) xs)) listt string))
;
;
;(defn permute-helper [listt xs]
;  (flattenn (map (fn [y] (concat-to-list y (remove-string y listt))) xs)))
;
;(defn repeat-n-times [func n listt]
;  (reduce (fn [a b] (func a)) listt (range n)))
;
;(defn permutation [listt n]
;  (repeat-n-times (fn [xs] (permute-helper listt xs)) (dec n) listt))
;
;(println (permutation ["a" "c" "b"] 3))
;

; primes
(defn add [mapp p step]
  (let [m (+ p step)]
    (if (mapp m)
      (recur mapp m step)
      (assoc mapp m step))))


(defn update-mapp [mapp p]
  (if-let[step (mapp p)] (-> (dissoc mapp p) (add p step)) (add mapp p (* 2 p)))
  )

(defn get-primes[mapp p]
  (if (contains? mapp p)
    (get-primes(update-mapp mapp p) (+ 2 p))
   (cons p (lazy-seq (get-primes (update-mapp mapp p) (+ 2 p)))))
  )
(defn primes [] (concat [2] (get-primes {} 3)))


(print (take 20 (primes)))







(use 'clojure.test)
(deftest eg-tests
  (is (= (take 20 (primes)) '(2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71)))
  (is (= (reduce + (take 5000 (primes))) 114455259))
  (is (=  (nth (take 20 (primes)) 0 ) 2)))

(run-tests)








; p-filter
(defn compp [x] (Thread/sleep 10) (if (= (mod x 2) 0) true false) )

(defn p-filter-1 [value-f coll]
  (let [chunk-size (int (Math/ceil (Math/sqrt (count coll)))),
        parts (partition-all chunk-size coll)]
    (->> parts
         (map (fn [coll1]
                (future (doall(filter (fn [x]  (value-f x) ) coll1)))))
         (doall)
         (map deref)
         (flatten)
         )))


(defn p-filter [pred coll]
  (if (empty? coll) '() (concat (p-filter-1 pred (take 100 coll)) (lazy-seq (p-filter pred (drop 100 coll))))
    ))









(use 'clojure.test)
(deftest eg-tests
  (is (= (take 100 (filter compp (iterate inc 1))) '(2 4 6 8 10 12 14 16 18 20 22 24 26 28 30 32 34 36 38 40 42 44 46 48 50 52 54 56 58 60 62 64 66 68 70 72 74 76 78 80 82 84 86 88 90 92 94 96 98 100 102 104 106 108 110 112 114 116 118 120 122 124 126 128 130 132 134 136 138 140 142 144 146 148 150 152 154 156 158 160 162 164 166 168 170 172 174 176 178 180 182 184 186 188 190 192 194 196 198 200)))
  (is (= (reduce + (take 100 (filter compp (iterate inc 1)))) (reduce + (take 100 (p-filter compp (iterate inc 1))))))
  (is (=  (nth (take 20 (p-filter compp (iterate inc 1))) 10) 22))
  )

(run-tests)