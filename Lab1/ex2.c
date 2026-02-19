#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <string.h>

int main(int argc, char *argv[])
{
    if (argc < 2)
    {
        perror("Nu ai fisier txt de deschis");
        return 0;
    }
    unsigned char buffer[20];
    unsigned char semne[] = ".,;/@#&*„?!"";";
    size_t nr;
    int fd = open(argv[1], O_RDONLY);
    if (fd == -1)
    {
        perror(argv[1]);
        exit(EXIT_FAILURE);
    }
    while ((nr = read(fd, buffer, 20)) > 0)
    {
        for (size_t i = 0; i < nr; i++)
            if (!strchr(semne, buffer[i]))
            {
                write(1, &buffer[i], 1);
            }
    }
    close(fd);
    return 0;
}