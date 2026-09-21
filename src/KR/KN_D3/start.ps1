param([switch]$Test, [switch]$SetupOnly)
$ErrorActionPreference = 'Stop'
$buildDir = Join-Path $PSScriptRoot '../../../out/KN_D3'
$libDir = Join-Path $PSScriptRoot '../../../lib'
$jar = Join-Path $libDir 'gson-2.13.2.jar'
New-Item -ItemType Directory -Force -Path $buildDir | Out-Null
New-Item -ItemType Directory -Force -Path $libDir | Out-Null
if (-not (Test-Path -LiteralPath $jar)) {
    Write-Host 'Lade JSON-Bibliothek Gson 2.13.2 von Maven Central ...'
    $download = "$jar.download"
    Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/com/google/code/gson/gson/2.13.2/gson-2.13.2.jar' -OutFile $download
    Move-Item -LiteralPath $download -Destination $jar -Force
}
if ($SetupOnly) {
    Write-Host 'Gson ist eingerichtet. Das Projekt kann in IntelliJ gestartet werden.'
    return
}
$sources = @(Get-ChildItem -LiteralPath (Join-Path $PSScriptRoot 'code') -Filter '*.java' | ForEach-Object FullName)
& javac -encoding UTF-8 --release 17 -cp $jar -d $buildDir @sources
if ($LASTEXITCODE -ne 0) { throw 'Kompilierung fehlgeschlagen.' }
$main = if ($Test) { 'KR.KN_D3.code.KryptoTest' } else { 'KR.KN_D3.code.Starter' }
& java -cp "$buildDir$([IO.Path]::PathSeparator)$jar" $main
if ($LASTEXITCODE -ne 0) { throw 'Ausfuehrung fehlgeschlagen.' }
