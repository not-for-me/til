#!/bin/sh

PHASE="build"
if [[ ! -z "$1" ]]; then
    echo "args!"
    PHASE=$1
fi

echo "Build Phase: $PHASE"

RUN_SCRIPT="java -cp cljs.jar:src clojure.main $PHASE.clj"

echo "Start: $RUN_SCRIPT"""
eval $RUN_SCRIPT