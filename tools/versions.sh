#!/bin/sh
set -eu

cd "$(CDPATH= cd "$(dirname "$0")/.." && pwd)"

[ "$#" -eq 0 ] || { printf "%s\n" "Usage: sh tools/versions.sh" >&2; exit 64; }
export LC_ALL=C PYTEST_DISABLE_PLUGIN_AUTOLOAD=1

check_version() {
  label=$1; expected=$2; mode=$3; pattern=$4
  shift 4
  command -v "$1" >/dev/null 2>&1 || { printf "BLOCKED: missing %s\n" "$1" >&2; exit 69; }
  if output=$("$@" 2>&1); then
    printf "%s\n" "$output"
  else
    status=$?
    printf "%s\n" "$output" >&2
    exit "$status"
  fi
  [ -n "$expected" ] || return 0
  actual=$(printf "%s\n" "$output" | sed -n "$pattern" | sed -n "1p")
  case "$mode:$actual" in
    "exact:$expected"|"prefix:$expected"|"prefix:$expected".*) return 0 ;;
    *)
      printf "BLOCKED: %s requires %s; found %s\n" "$label" "$expected" "${actual:-unrecognized version}" >&2
      exit 69 ;;
  esac
}

check_version 'Java' '21' 'prefix' 's/.*version "\([^"]*\)".*/\1/p' java -version
check_version 'javac' '21' 'prefix' 's/^javac \([0-9][[:alnum:].+_-]*\).*/\1/p' javac -version
check_version 'Gradle' '9.6.0' 'exact' 's/^Gradle \([0-9][[:alnum:].+_-]*\).*/\1/p' gradle --version
check_version 'Git' '' 'exact' '' git --version

printf "%s\n" "CLI versions checked; dependency caches, browser and SUT readiness are NOT_VERIFIED."
