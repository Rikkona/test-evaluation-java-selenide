---
name: create-ui-test
description: "Creates or extends a UI test using the current architecture. Use when asked to add a browser scenario, automate a manual case, or extend login coverage."
---

# create-ui-test

Follow [AGENTS.md](../../../AGENTS.md) throughout.

1. Read [knowledge/architecture/overview.md](../../../knowledge/architecture/overview.md) and inspect its linked test example.
2. Find the closest existing scenario; follow the overview's Page Object link only when that layer exists.
3. Read [knowledge/architecture/test-data.md](../../../knowledge/architecture/test-data.md) for data and environment constraints.
4. Reuse existing objects/fixtures. Keep the current inline style when no Page Object layer exists.
5. Add one scenario that mirrors the canonical test shape verbatim — same call-chain form and check count; only the data and expected outcome change. Assert exactly what the scenario verifies: no precondition visibility checks before a retrying assertion, no extra state checks after it.
6. Read [knowledge/testing/run-tests.md](../../../knowledge/testing/run-tests.md); verify with tools/versions.sh and tools/compile-tests.sh, then run only the new test when authorized.
   tools/run-tests.sh contains explicit snapshot selectors. For a new or renamed test, update its case dispatch from the real runner selector; never substitute a full-suite run.
7. Fix only defects in your own implementation; report product/environment problems rather than hiding them.

## Completion
List changed files and scenario, checks performed and their evidence; distinguish a compiled test from an executed test.
Do not introduce new architecture, dependencies or a second Page Object for an existing page.

Example request: Add a UI test for an existing authorization scenario.
