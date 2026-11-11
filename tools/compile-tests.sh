#!/bin/sh
set -eu

cd "$(CDPATH= cd "$(dirname "$0")/.." && pwd)"

[ "$#" -eq 0 ] || { printf "%s\n" "Usage: sh tools/compile-tests.sh" >&2; exit 64; }
printf '%s\n' '+ gradle --offline testClasses'
gradle --offline testClasses
printf "%s\n" "Compilation/collection completed; no browser tests were executed."
