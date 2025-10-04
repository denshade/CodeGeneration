# CompileJava.ps1
# Description: Compiles all Java files and includes all JARs in ./lib using a javac argfile.

# Base directory
$BaseDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
Set-Location $BaseDir

# Directories
$LibDir = Join-Path $BaseDir "lib"
$OutputDir = Join-Path $BaseDir "bin"

# Ensure output directory exists
if (-not (Test-Path $OutputDir)) {
    New-Item -ItemType Directory -Path $OutputDir | Out-Null
}

# Collect all .jar files (classpath)
$Classpath = ""
if (Test-Path $LibDir) {
    $JarFiles = Get-ChildItem -Path $LibDir -Filter *.jar -Recurse | ForEach-Object { $_.FullName }
    if ($JarFiles) {
        # IMPORTANT: Use correct path separator for Windows
        $Classpath = [string]::Join(";", $JarFiles)
    }
}

# Collect all .java files
$JavaFiles = Get-ChildItem -Path $BaseDir -Filter *.java -Recurse | ForEach-Object { $_.FullName }
if (-not $JavaFiles) {
    Write-Host "No Java files found."
    exit 1
}

# Create temporary javac argfile
$ArgFile = Join-Path $BaseDir "javac_args.txt"

# Build argfile content
$ArgLines = @()
$ArgLines += '-encoding UTF-8'
$ArgLines += "-d"
$ArgLines += "`"$OutputDir`""
if ($Classpath -ne "") {
    # NOTE: For javac, it's '-classpath', not '-cp'
    $ArgLines += "-classpath"
    $ArgLines += "`"$Classpath`""
}
$ArgLines += $JavaFiles

# Write to argfile
Set-Content -Path $ArgFile -Value $ArgLines

# Run javac
Write-Host "Compiling Java sources..."
javac "@$ArgFile" -classpath $Classpath -d bin

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Compilation successful. Classes saved in '$OutputDir'."
} else {
    Write-Host "❌ Compilation failed."
}

# Uncomment to clean up argfile after compile
# Remove-Item $ArgFile -Force
