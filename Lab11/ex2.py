import subprocess

def execute(cmd):
    comenzi =[c.strip() for c in cmd.split("|")]

    if not comenzi or not comenzi[0]:
        print("Comanda goala")
        return

    proces_anterior = None
    procese = []

    for c in comenzi:
        arg = c.split()

        if proces_anterior:
            stdinput = proces_anterior.stdout
        else:
            stdinput = None
        proces_curent = subprocess.Popen(arg,stdin=stdinput, stdout=subprocess.PIPE,text = True)

        if proces_anterior:
            proces_anterior.stdout.close()

        proces_anterior = proces_curent
        procese.append(proces_curent)

    output,err = proces_anterior.communicate()
    return output

def main():
    comanda_in = input("Comanda in: ")

    rezultat = execute(comanda_in)

    print(rezultat)

if __name__ == "__main__":
    main()