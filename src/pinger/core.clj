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
  (do (try (str (timed-ping "ya.ru" 5000))
           (catch Exception e (str "caught exception: " (.getMessage e))))
      (Tread/sleep 5000) ))

(def log "PUT LOG.TXT FILEPATH HERE")

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
      (loop [attempt 1 ]
           (do
             (spit log (str  attempt " "  (try-catch-timed-ping) "\n") :append true )
             (println (str "I ping to ya.ru, this is my: " attempt " attempt." ))
             (recur (inc attempt)))))
