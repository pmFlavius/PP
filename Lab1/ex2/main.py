def main():
    with open("fisier.txt","r") as f:
        continut=f.read()

    semne = "123456789.,!?;:-/@#&%*^!()[]{}'\""
    

    #eliminare spatii multiple
    text=""
    L=continut.split()
    for x in L:
        text+=(x+" ")
    
    #eliminare semne de punctuatie si numere
    rezultat = ""
    for c in text:
        if c not in semne:
            rezultat += c
            
    #toate caracterele vor fi uppercase
    rezultat=rezultat.upper()

    print("Text final: \n" + rezultat)


if __name__=="__main__":
    main()