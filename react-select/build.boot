(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.5.2"  :scope "test"]
                  [cljsjs/react "0.14.3-0"]
                  [cljsjs/react-dom "0.14.3-1"]
                  [cljsjs/classnames "2.2.3-0"]
                  [cljsjs/react-input-autosize "0.6.10-0"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "1.0.0-beta14")
(def +version+ (str +lib-version+ "-0"))

(task-options!
 pom  {:project     'cljsjs/react-select
       :version     +version+
       :description "A flexible and beautiful Select Input control for ReactJS with multiselect, autocomplete and ajax support."
       :url         "http://jedwatson.github.io/react-select/"
       :scm         {:url "https://github.com/cljsjs/packages"}
       :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(require '[boot.core :as c]
         '[boot.tmpdir :as tmpd]
         '[clojure.java.io :as io]
         '[clojure.string :as string])

(deftask package []
  (comp
   (download :url (str "https://github.com/JedWatson/react-select/archive/v" +lib-version+ ".zip")
             :checksum "CE1352F40BC8BAE6A99D5C99657C2354"
             :unzip true)

   (sift :move {#"^react-select.*[/ \\]dist[/ \\]react-select.js$" "cljsjs/react-select/development/react-select.inc.js"
                #"^react-select.*[/ \\]dist[/ \\]react-select.min\.js$" "cljsjs/react-select/production/react-select.min.inc.js"
                #"^react-select.*[/ \\]dist[/ \\]react-select.css$" "cljsjs/react-select/common/react-select.inc.css"})

   (sift :include #{#"^cljsjs"})

   (deps-cljs :name "cljsjs.react-select"
              :requires ["cljsjs.react"
                         "cljsjs.react.dom"
                         "cljsjs.classnames"
                         "cljsjs.react-input-autosize"])
   (pom)
   (jar)))
