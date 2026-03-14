package org.example

import java.util.Date

interface PaymentMethod{
    fun pay(fee: Double):Boolean
}

class CashPayment(private var availableAmount: Double) : PaymentMethod{
    override fun pay(fee: Double): Boolean {
        if(availableAmount >= fee) {
            availableAmount -= fee;
            return true;
        }
        return false;
    }
}

class BankAccount(private var availableAmount: Double, private var cardNumber: String, private var expirationDate: Date, private var cvvCode: Int, private var userName:String){
    public fun updateAmount(value: Double): Boolean{
        if(availableAmount >= value){
            availableAmount -= value;
            return true
        }
        return false
    }
}

class CardPayment(private var bankAccount: BankAccount) : PaymentMethod{
    override fun pay(fee: Double): Boolean {
        return bankAccount.updateAmount(fee)
    }
}

data class Movie(val name: String,val price: Double)
data class Seat(val row: Int, val column: Int, var reserved: Boolean = false)
data class Ticket(val movie: Movie, val seat: Seat)

class BookingSystem(){
    public fun bookTicket(ticket: Ticket, paymentMethod: PaymentMethod) {
        if(ticket.seat.reserved==false){
            if(paymentMethod.pay(ticket.movie.price)) {
                ticket.seat.reserved = true
                println("Bilet achizionat cu succes")
            }
            else{
             println("Nu sunt suficienti bani")
            }
        }
        else {
            println("Loc rezervat")
        }

    }
}


fun main(args: Array<String>) {
    val movie = Movie("Batman", 30.0)
    val seat = Seat(10, 5)
    val ticket = Ticket(movie, seat)

    val wallet= CashPayment(500.0)
    val system = BookingSystem()
    system.bookTicket(ticket,wallet)

    system.bookTicket(ticket,wallet)

}

