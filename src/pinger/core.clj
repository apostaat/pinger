(ns pinger.core
  (:gen-class))

(defn timed-ping
      "Time an .isReachable ping to a given domain"
      [domain timeout]
      (let [addr (java.net.InetAddress/getByName domain)
            start (. System (nanoTime))
            result (.isReachable addr timeout)
            total (/ (double (- (. System (nanoTime)) start)) 1000000.0)]
           {:time total
            :result result}))

(defn try-catch-timed-ping
  "Try/catch and get string of result for timed ping"
  []
   (try (str (timed-ping "ya.ru" 5000))
           (catch Exception e (str "caught exception: " (.getMessage e)))))

(def log "/Users/artemalekseev/Downloads/lein projects/pinger/resources/log.txt")

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
      (loop [attempt 1 ]
           (do
             (spit log (str  attempt " "  (try-catch-timed-ping) " " (java.util.Date.) "\n") :append true )
             (println (str "I ping to ya.ru, this is my: " attempt " attempt. " (try-catch-timed-ping) " " (java.util.Date.) ))
             (Thread/sleep 5000)
             (recur (inc attempt)))))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (loop [attempt 1 ]
    (let [x (try-catch-timed-ping)
          y (java.util.Date.)]
      (do
        (spit log (str  attempt " "  x " " y "\n") :append true )
        (println (str "I ping to ya.ru, this is my: " attempt " attempt. " x " " y ))
        (Thread/sleep 5000)
        (recur (inc attempt)))
      )))
