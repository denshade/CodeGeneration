# Define paths
$depsFile = "config\dependencies.txt"
$libDir = "lib"

# Ensure lib directory exists
if (-not (Test-Path $libDir)) {
    New-Item -ItemType Directory -Path $libDir | Out-Null
}

# Loop through each URL in the dependencies file
Get-Content $depsFile | ForEach-Object {
    $url = $_.Trim()

    # Skip empty lines
    if ([string]::IsNullOrWhiteSpace($url)) {
        return
    }

    # Extract filename (last part after '/')
    $outfile = Split-Path $url -Leaf
    $outpath = Join-Path $libDir $outfile

    # Check if file already exists
    if (Test-Path $outpath) {
        Write-Host "Skipping: $outfile (already exists)"
    } else {
        Write-Host "Downloading: $outfile"
        try {
            Invoke-WebRequest -Uri $url -OutFile $outpath
            Write-Host "Downloaded: $outfile"
        } catch {
            Write-Host "Failed to download $url : $($_.Exception.Message)" -ForegroundColor Red
        }
    }
}