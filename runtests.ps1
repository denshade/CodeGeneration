# RunJunitTests.ps1
# Runs all JUnit tests from compiled .class files in /bin using the console launcher.

$BaseDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
Set-Location $BaseDir

$BinDir = Join-Path $BaseDir "bin"
$LibDir = Join-Path $BaseDir "lib"

if (-not (Test-Path $BinDir)) {
    Write-Host "No 'bin' directory found. Compile sources first."
    exit 1
}

# Collect all JARs for classpath
$JarFiles = Get-ChildItem -Path $LibDir -Filter *.jar -Recurse | ForEach-Object { $_.FullName }
$Classpath = [string]::Join(";", @($BinDir) + $JarFiles)

# Find the JUnit ConsoleLauncher JAR
$ConsoleJar = $JarFiles | Where-Object { $_ -match "junit-platform-console-standalone.*\.jar" }

if (-not $ConsoleJar) {
    Write-Host "❌ Could not find junit-platform-console-standalone JAR in lib/."
    Write-Host "Download it from: https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/"
    exit 1
}

Write-Host "Running JUnit tests..."
Write-Host "Using classpath:"
Write-Host $Classpath

# Run all tests in the bin/ directory
& java -jar $ConsoleJar --class-path "$Classpath" --scan-classpath "$BinDir"

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ All tests passed."
} else {
    Write-Host "❌ Some tests failed."
}
