---
name: run-tests
description: "Runs focused existing tests and reports evidence. Use when asked to run tests, check changes, execute a class/method, or verify a test project."
---

# run-tests

Follow [AGENTS.md](../../../AGENTS.md) throughout.

1. Read [knowledge/testing/run-tests.md](../../../knowledge/testing/run-tests.md).
2. Inspect existing work with [tools/git.sh](../../../tools/git.sh); list scopes using `sh tools/run-tests.sh list` and choose the smallest requested scope.
3. Run [tools/versions.sh](../../../tools/versions.sh) with `sh`; separately verify dependency caches, browser availability and SUT authorization.
4. Run `sh tools/compile-tests.sh` via [tools/compile-tests.sh](../../../tools/compile-tests.sh); if it fails, stop before any browser run.
5. Only after authorization, use [tools/run-tests.sh](../../../tools/run-tests.sh) with `--allow-sut one CASE`, `--allow-sut group AREA`, or explicitly requested `--allow-sut all`.
6. Inspect counts and available artifacts, not only exit status. Never treat skipped or undiscovered tests as passed.
7. For a failure, use [knowledge/testing/debugging.md](../../../knowledge/testing/debugging.md) and report its stage.

## Completion
Report PASS / FAIL / BLOCKED, command, scope, versions, exit code, counts and evidence paths.
Do not alter or delete tests to obtain a successful run. Do not claim an unexecuted phase passed.

Example request: Run the existing login test and show the results.
