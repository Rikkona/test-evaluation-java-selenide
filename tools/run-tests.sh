#!/bin/sh
set -eu

cd "$(CDPATH= cd "$(dirname "$0")/.." && pwd)"

usage() {
  printf "%s\n" "Usage: sh tools/run-tests.sh list | --allow-sut one CASE | --allow-sut group AREA | --allow-sut all" >&2
  exit 64
}
allow_sut=false
if [ "${1-}" = "--allow-sut" ]; then allow_sut=true; shift; fi
case "${1-}" in
  list)
    [ "$#" -eq 1 ] || usage
    printf '%s\n' 'login-success [login] Successful login' 'login-failure [login] Unsuccessful login' 'login-empty-password [login] Login without a password' 'login-unknown-username [login] Login with an unknown username' 'login-empty-username [login] Login without a username' 'login-empty-credentials [login] Login without a username or password'
    exit 0 ;;
  one|group) [ "$#" -eq 2 ] || usage ;;
  all) [ "$#" -eq 1 ] || usage ;;
  *) usage ;;
esac

require_sut() {
  [ "$allow_sut" = true ] || {
    printf "%s\n" "BLOCKED: approve external SUT access and any selected registration writes before using --allow-sut." >&2
    exit 77
  }
}

case "$1:${2-}" in
  'one:login-success')
    require_sut
    printf '%s\n' '+ gradle --offline test --tests '\''tests.LoginTest.successfulLoginTest'\'''
    exec gradle --offline test --tests 'tests.LoginTest.successfulLoginTest'
    ;;
  'one:login-failure')
    require_sut
    printf '%s\n' '+ gradle --offline test --tests '\''tests.LoginTest.wrongPasswordTest'\'''
    exec gradle --offline test --tests 'tests.LoginTest.wrongPasswordTest'
    ;;
  'one:login-empty-password')
    require_sut
    printf '%s\n' '+ gradle --offline test --tests '\''tests.LoginTest.missingPasswordTest'\'''
    exec gradle --offline test --tests 'tests.LoginTest.missingPasswordTest'
    ;;
  'one:login-unknown-username')
    require_sut
    printf '%s\n' '+ gradle --offline test --tests '\''tests.LoginTest.wrongUsernameTest'\'''
    exec gradle --offline test --tests 'tests.LoginTest.wrongUsernameTest'
    ;;
  'one:login-empty-username')
    require_sut
    printf '%s\n' '+ gradle --offline test --tests '\''tests.LoginTest.missingUsernameTest'\'''
    exec gradle --offline test --tests 'tests.LoginTest.missingUsernameTest'
    ;;
  'one:login-empty-credentials')
    require_sut
    printf '%s\n' '+ gradle --offline test --tests '\''tests.LoginTest.missingCredentialsTest'\'''
    exec gradle --offline test --tests 'tests.LoginTest.missingCredentialsTest'
    ;;
  'group:login')
    require_sut
    printf '%s\n' '+ gradle --offline test --tests '\''tests.LoginTest'\'''
    exec gradle --offline test --tests 'tests.LoginTest'
    ;;
  'all:')
    require_sut
    printf '%s\n' '+ gradle --offline test'
    exec gradle --offline test
    ;;
  *) usage ;;
esac
