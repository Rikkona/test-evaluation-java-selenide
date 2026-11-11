---
name: fix-failing-test
description: "Investigates failing or flaky tests and minimally fixes proven test defects. Use when a test fails, becomes unstable, or breaks after changes."
---

# fix-failing-test

Follow [AGENTS.md](../../../AGENTS.md) throughout.

1. Read [knowledge/testing/run-tests.md](../../../knowledge/testing/run-tests.md) and [knowledge/testing/debugging.md](../../../knowledge/testing/debugging.md).
2. Inspect current Git work with [tools/git.sh](../../../tools/git.sh), then the failing test, original command, stack trace and available artifacts.
3. Verify with tools/versions.sh and tools/compile-tests.sh, then reproduce the smallest authorized scope with [tools/run-tests.sh](../../../tools/run-tests.sh); do not invent a failure for demonstration.
4. Classify evidence as test defect, product defect, environment issue, test data issue, or suspected flaky behavior.
5. If the requested fix is a proven test defect, make the smallest correction. Ask when ownership/cause is unclear.
6. Do not weaken assertions, add sleeps, change business logic, delete evidence or clean server data to hide a failure.
7. Re-run the same scope after the fix; if flaky, collect repeated isolated runs before claiming a resolution.

## Completion
Report root cause with evidence, the minimal change, exact verification results and any remaining uncertainty.
If reproduction is blocked, state BLOCKED rather than presenting a hypothesis as a confirmed fix.

Example request: Investigate why this login test fails.
