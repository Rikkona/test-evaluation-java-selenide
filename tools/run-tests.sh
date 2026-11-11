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
    printf '%s\n' 'login-success [login] Successful login' 'login-failure [login] Unsuccessful login' 'login-empty-password [login] Login without a password' 'login-unknown-username [login] Login with an unknown username' 'login-empty-username [login] Login without a username' 'login-empty-credentials [login] Login without a username or password' 'login-short-username [login] Login with a username below the minimum length'
    printf '%s\n' 'login-short-password [login] Login with a password below the minimum length'
    printf '%s\n' 'registration-success [registration] Successful registration' 'registration-duplicate [registration] Duplicate registration' 'registration-mismatched-passwords [registration] Registration with mismatched passwords'
    printf '%s\n' 'registration-empty-password [registration] Registration without a password'
    printf '%s\n' 'registration-empty-username [registration] Registration without a username'
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
  'one:login-short-username')
    require_sut
    printf '%s\n' '+ gradle --offline test --tests '\''tests.LoginTest.shortUsernameTest'\'''
    exec gradle --offline test --tests 'tests.LoginTest.shortUsernameTest'
    ;;
  'one:login-short-password')
    require_sut
    printf '%s\n' '+ gradle --offline test --tests '\''tests.LoginTest.shortPasswordTest'\'''
    exec gradle --offline test --tests 'tests.LoginTest.shortPasswordTest'
    ;;
  'one:registration-success')
    require_sut
    printf '%s\n' '+ gradle --offline test --tests '\''tests.RegistrationTest.successfulRegistrationTest'\'''
    exec gradle --offline test --tests 'tests.RegistrationTest.successfulRegistrationTest'
    ;;
  'one:registration-duplicate')
    require_sut
    printf '%s\n' '+ gradle --offline test --tests '\''tests.RegistrationTest.duplicateRegistrationTest'\'''
    exec gradle --offline test --tests 'tests.RegistrationTest.duplicateRegistrationTest'
    ;;
  'one:registration-mismatched-passwords')
    require_sut
    printf '%s\n' '+ gradle --offline test --tests '\''tests.RegistrationTest.passwordMismatchTest'\'''
    exec gradle --offline test --tests 'tests.RegistrationTest.passwordMismatchTest'
    ;;
  'one:registration-empty-password')
    require_sut
    printf '%s\n' '+ gradle --offline test --tests '\''tests.RegistrationTest.missingPasswordTest'\'''
    exec gradle --offline test --tests 'tests.RegistrationTest.missingPasswordTest'
    ;;
  'one:registration-empty-username')
    require_sut
    printf '%s\n' '+ gradle --offline test --tests '\''tests.RegistrationTest.missingUsernameTest'\'''
    exec gradle --offline test --tests 'tests.RegistrationTest.missingUsernameTest'
    ;;
  'group:registration')
    require_sut
    printf '%s\n' '+ gradle --offline test --tests '\''tests.RegistrationTest'\'''
    exec gradle --offline test --tests 'tests.RegistrationTest'
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
