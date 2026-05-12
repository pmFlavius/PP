import asyncio

async def suma(coada, name):
    while True:

        valoare = await coada.get()
        total = 0
        print(f"{name}: calculeaza suma pana la {valoare}")

        for i in range(valoare + 1):
            total += i

        print(f"{name}: suma = {total}")

        coada.task_done()


async def main():
    coada = asyncio.Queue()
    #trebuie toate call urile la coada facute cu await 
    print("Introduceti 4 valori:")
    for i in range(4):
        nr = int(input())
        await coada.put(nr)

    #creez task uri
    taskuri = [
        asyncio.create_task(suma(coada, "Task "))
    ]


    await coada.join()
    await asyncio.gather()


if __name__ == "__main__":
    asyncio.run(main())