; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

[Setup]
AppName=Dialogix
AppVerName=Dialogix @@DIALOGIX.VERSION_MAJOR@@.@@DIALOGIX.VERSION_MINOR@@ for @@LICENSE.PRINCIPAL_INVESTIGATOR@@ for @@LICENSE.STUDY_ALIAS@@ Study
AppPublisher=Thomas M. White, MD
AppCopyright=Copyright � 2000-2002 Thomas M. White, MD
AppPublisherURL=http://www.dianexus.org
AppSupportURL=http://www.dianexus.org
AppUpdatesURL=http://www.dianexus.org
DefaultDirName=@@DIALOGIX.HOME@@
DisableDirPage=yes
DefaultGroupName=Dialogix
AllowNoIcons=yes
LicenseFile=@@DIALOGIX.HOME@@\License.txt
InfoAfterFile=@@DIALOGIX.HOME@@\README.txt
; uncomment the following line if you want your installation to run on NT 3.51 too.
; MinVersion=4,3.51

[Tasks]
Name: "desktopicon"; Description: "Create a &desktop icon"; GroupDescription: "Additional icons:"; MinVersion: 4,4
Name: "quicklaunchicon"; Description: "Create a &Quick Launch icon"; GroupDescription: "Additional icons:"; MinVersion: 4,4; Flags: unchecked

[Files]
Source: "@@DIALOGIX.HOME@@\*.*"; DestDir: "{app}"; CopyMode: alwaysoverwrite; Flags: recursesubdirs

[INI]
Filename: "{app}\dialogix.url"; Section: "InternetShortcut"; Key: "URL"; String: "http://www.dianexus.org"

[Icons]
Name: "{group}\Dialogix"; Filename: "{app}\dialogix.pif"; WorkingDir: "{app}"; IconFilename: "{app}\dialogix.ico"
Name: "{group}\Dialogix on the Web"; Filename: "{app}\dialogix.url"
Name: "{userdesktop}\Dialogix"; Filename: "{app}\dialogix.pif"; WorkingDir: "{app}"; IconFilename: "{app}\dialogix.ico"; MinVersion: 4,4; Tasks: desktopicon
Name: "{userappdata}\Microsoft\Internet Explorer\Quick Launch\Dialogix"; Filename: "{app}\dialogix.pif"; IconFilename: "{app}\dialogix.ico"; WorkingDir: "{app}"; MinVersion: 4,4; Tasks: quicklaunchicon
Name: "{group}\View Working Files"; Filename: "{app}\view_working_dir.bat"; WorkingDir: "{app}"; IconFilename: "{app}\folder.ico"
Name: "{group}\View Completed Files"; Filename: "{app}\view_completed_dir.bat"; WorkingDir: "{app}"; IconFilename: "{app}\folder.ico"


[Run]
Filename: "{app}\dialogix.pif"; Description: "Launch Dialogix"; Flags: shellexec postinstall skipifsilent runminimized

[UninstallDelete]
Type: files; Name: "{app}\dialogix.url"

