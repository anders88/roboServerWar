(ns robowar.boardprosess
  [:use clojure.test]
  )

(def boardsize 50)

(with-test
(defn pos-after-move [pos facing]
  (let [more (fn [x] (min (inc x) boardsize)) less (fn [x] (max (dec x) 0))]
  (cond 
     (= facing :east ) [(first pos) (more (second pos))]
     (= facing :south ) [(more (first pos)) (second pos)]
     (= facing :west ) [(first pos) (less (second pos))]
     (= facing :north ) [(less (first pos)) (second pos)]
     :else pos
  )))
  (is (= [1 2] (pos-after-move [1 1] :east)))
  (is (= [1 boardsize] (pos-after-move [1 boardsize] :east)))
  (is (= [2 1] (pos-after-move [1 1] :south)))
  (is (= [1 0] (pos-after-move [1 1] :west)))
  (is (= [0 1] (pos-after-move [1 1] :north)))
)


(with-test
  (defn move [tank]
    (assoc tank :pos (pos-after-move (tank :pos) (tank :facing))))
  (is (= {:pos [0 1] :facing :east} (move {:pos [0 0] :facing :east})))
  )

(with-test
  (defn rotate-right [tank]
     (assoc tank :facing ({:north :east, :east :south, :south :west, :west :north} (tank :facing))))
  (is (= {:pos [1 1] :facing :east} (rotate-right {:pos [1 1] :facing :north})))
  (is (= {:pos [1 1] :facing :north} (rotate-right {:pos [1 1] :facing :west})))
  )

(with-test
  (defn rotate-left [tank]
     (assoc tank :facing ({:north :west, :east :north, :south :east, :west :south} (tank :facing))))
  (is (= {:pos [1 1] :facing :west} (rotate-left {:pos [1 1] :facing :north})))
  (is (= {:pos [1 1] :facing :south} (rotate-left {:pos [1 1] :facing :west})))
  )

