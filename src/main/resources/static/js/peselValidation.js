function ParsePesel()
{
    var s = document.getElementById("pesel").value;
    //Sprawdź długość, musi być 11 znaków
    if (SetError(s.length != 11))
        return;

    //Sprawdź, czy wszystkie znaki to cyfry
    var aInt = new Array();
    for (i=0;i<11; i++)
    {
        aInt[i] = parseInt(s.substring(i,i+1));
        if (isNaN(aInt[i]))
        {
            SetError(1);
            return;
        }
    }

    //Sprawdź sumę kontrolną
    var wagi = [1,3,7,9,1,3,7,9,1,3,1];
    var sum=0;
    for (i=0;i<11;i++)
        sum+=wagi[i]*aInt[i];
    if (SetError((sum%10)!=0))
        return;

    //Policz rok z uwzględnieniem XIX, XXI, XXII i XXIII wieku
    var rok = 1900+aInt[0]*10+aInt[1];
    if (aInt[2]>=2 && aInt[2]<8)
        rok+=Math.floor(aInt[2]/2)*100;
    if (aInt[2]>=8)
        rok-=100;

    var miesiac = (aInt[2]%2)*10+aInt[3];
    var dzien = aInt[4]*10+aInt[5];

    //Sprawdź poprawność daty urodzenia
    if (SetError(!checkDate(dzien,miesiac,rok)))
        return;
    var plec = (aInt[9]%2==1)?"man":"woman";

    //Uzupełnij pola wchodzące w skład numeru PESEL
    // document.getElementById("rok").value = rok;
    // document.getElementById("miesiac").value = miesiac;
    // document.getElementById("dzien").value = dzien;
    document.getElementById("sex").value = plec;
    document.getElementById("birthday").value = dzien.toString().concat(".","0".concat(miesiac.toString()).slice(-2),".",rok.toString());
}
function SetError(c){
    // document.getElementById("hasError").style.visibility=(c?"visible":"hidden");
    return c;
}
function checkDate(d,m,y)
{
    var dt = new Date(y,m-1,d);
    return dt.getDate()==d &&
        dt.getMonth()==m-1 &&
        dt.getFullYear()==y;
}