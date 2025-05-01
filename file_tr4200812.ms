file_tr4200812.ms
name: tr4200812 MSP Patch

on:
  push:
    branches: [ main ]

jobs:
  apply_patch:
    runs-on: windows-latest  # Important: Use a Windows runner for MSI files
    steps:
      - name: Checkout repository
        uses: actions/checkout@v3

      - name: Apply the MSP patch
        run: |
          msiexec.exe /p file_tr4200812.ms /qn  # /qn for silent install
