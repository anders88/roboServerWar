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