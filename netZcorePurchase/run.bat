
set projectpath=C:\Users\chandra.rai\Desktop\netZcorePurchase
echo %projectpath%
set classpath=%projectpath%\bin;%projectpath%\lib\*
echo %classpath%
java org.testng.TestNG %projectpath%\testng.xml
