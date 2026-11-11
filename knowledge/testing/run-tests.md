# Running tests

POSIX shell commands, run from the project root.
The harness does not install dependencies, launch services or run tests when enabled.

## Prerequisites and permissions
Read [../stack/versions.md](../stack/versions.md) and verify the installed versions:
```bash
java -version
javac -version
gradle --version
git --version
```
Verify dependencies, browser/driver availability and SUT readiness before execution; first use may download or resolve them — ask before installs/downloads, network access or environment repair.
The configured external SUT requires explicit authorization; registration additionally writes persistent server data.

## Tools
Run the scripts with `sh` — no exec bit needed; POSIX sh + dirname + sed required, PowerShell unsupported.
Scripts resolve the project root relative to themselves, including paths with spaces.
- [tools/versions.sh](../../tools/versions.sh): verify selected CLI pins. Git, npm and pip have no separate pin; only their availability/version command is checked.
- [tools/compile-tests.sh](../../tools/compile-tests.sh): compile or collect, never a browser-test PASS.
- [tools/run-tests.sh](../../tools/run-tests.sh): list selected case IDs and groups; execute only an explicit scope.
- [tools/git.sh](../../tools/git.sh): status, staged/unstaged diff, and whitespace checks in this project; no add/commit/push/reset or repository initialization.
```bash
sh tools/git.sh status
sh tools/versions.sh
sh tools/compile-tests.sh
sh tools/run-tests.sh list
```
```bash
sh tools/run-tests.sh --allow-sut one 'login-success'
sh tools/run-tests.sh --allow-sut group 'login'
sh tools/run-tests.sh --allow-sut all
```
`--allow-sut` acknowledges approval for the selected target and any registration writes; it is not a sandbox and does not replace client permissions.
Gradle/Maven/npm commands use offline dependency resolution; a missing cached dependency is BLOCKED, not permission to install it.
Build scripts, pytest plugins and driver managers can execute code or use the network — offline flags do not sandbox them; verify permissions separately.
Preflight checks CLI versions only — not dependency, browser or SUT readiness; pytest plugin autoload is disabled only for its own version check.
Tools preserve native output and exit codes: 64 = invalid arguments/scope, 69 = missing/mismatched prerequisites or no selected cases, 77 = SUT approval not acknowledged.
Tools never interpret shell text or forward runner flags; after adding/renaming tests, update the explicit selectors from source — this is a snapshot, not runtime discovery.
Git tools disable external diff/textconv helpers, fsmonitor and index-refresh writes; existing Git configuration and repository boundaries still apply.
No hooks are installed or activated.

## Bootstrap reference
Only execute setup commands after authorization. Wrappers and npm lockfiles are not supplied.

## Compile / syntax / discovery only
```bash
gradle --offline testClasses
```
These checks do not execute the browser scenarios. Discovery for the full selection should contain 1 tests; a focused filter intentionally selects fewer.

## Focused execution
One test:
```bash
gradle --offline test --tests 'tests.LoginTest.successfulLoginTest'
```
One class/module:
```bash
gradle --offline test --tests 'tests.LoginTest'
```

## All selected tests
Use the full suite only when that scope is requested and the target/data changes are authorized:
```bash
gradle --offline test
```
No tags, categories/groups or custom testE2e task are configured. Do not guess tag filters from another project.
Commands omit clean so the previous failure evidence is not silently deleted.

## Evidence
Gradle HTML/JUnit reports are disabled in this snapshot (`reports.*.required = false`). Do not claim these reports exist.
Record command, scope, actual versions, exit code and executed/passed/failed/skipped counts. A zero exit code with no executed tests is not a test pass.
Reusing results from an earlier run is not verification of the current changes.

## CI and integrations
No CI file is generated.
CI jobs and TestOps/Jira/Confluence writes are separate permission-gated actions, not part of a local test request by default.
No TestOps upload is selected.
