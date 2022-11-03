package com.example.myfirstcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar

private lateinit var resultTextView: TextView
private var operand = 0.0
private var operation = ""
private var equalityExecutionCount = 0
private var toSaveOperand = 0.0
private var startANew = false

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resultTextView = findViewById(R.id.resultTextView)

        val hideName: ActionBar? = supportActionBar
        hideName?.hide()
    }
    fun numberClick(clickedView: View) {
        if (clickedView is TextView) {
            //ნებისმიერი კალკულატორის გამოყენებისას ვნახავთ რომ როდესაც ოპერაციას ვასრულებთ ანუ ტოლობის ღილაკს ვაჭერთ
            //ახალი ციფრის აკრეფისას ეკრანზე პირდაპირ ეს ციფრი იბეჭდება და აღარ გვჭირდება clear ის დაჭერა
            //startANew სწორედ ამაში გვეხმარება
            //შესაბამისად ეს ცვლადი equalsClick ში ხდება true
            if(startANew){
                //თუ მომხარებელმა მაგალითისთვის 5 + 5 ის აკრეფის შემდეგ დააჭირა ტოლობას ხოლო ამის შემდეგ დააჭირა კიდევ + 5
                //ანუ ხუთის აკრეფამდე resultTextView ცარიელია, მაშინ არ მოხდება operand ის და operation ის
                //განულება
                if(resultTextView.text!= "") {
                    //თუ მომხარებელმა დაასრულა ოპერაცია და ამის შემდეგ დააჭირა წერტილს დაიბეჭდება -> 0.

                    if(clickedView.text == ".")
                    {
                        resultTextView.text = "0."
                    }
                    else {
                        resultTextView.text = ""
                    }
                    //ახალი ციფრის აკრეფის შედეგად შესაბამისად ნულდება ეს ორი ცვლადი
                    operand = 0.0
                    operation = ""
                }
                //ზემოთ მოყვანილი მაგალითის მიუხედავად
                //რადგან ტოლობის ნიშანს დაეჭირა, რაც ნიშნავს რომ ახალი ოპერაცია უნდა დაიწყოს, ეს ცლვლადი გახდება კვლავ 0
                equalityExecutionCount = 0
                startANew = false
            }

            var text0 = resultTextView.text.toString()
            var number = clickedView.text.toString()

            if(text0 == "0" && number!= "."){
                text0 = ""
            }
            if(resultTextView.text.toString().contains(".") && number == "."){
                number = ""
            }
            var result = text0 + number
            if(result == ".")
            {
                result = "0."
            }
            resultTextView.text = result
        }
    }


    fun operationClick(clickedView: View) {
        if (clickedView is TextView) {

            operation = clickedView.text.toString()
            //თუ დავუშვათ მომხარებელმა აკრიფა ციფრი 5 და ჯერ დააჭირა პლიუსის ღილაკს ხოლო შემდეგ მინუსის
            //საჭიროა პროგრამამ იცოდეს რომ უკვე განულებული ციფრი 5 ანუ ცარიელი ადგილი აღარ გადააქციოს double ად
            // და შესაბამისად არ დაიქრაშოს
            if(resultTextView.text.toString()!= "") {
                operand = resultTextView.text.toString().toDouble()
            }
            resultTextView.text = ""


        }
    }

    fun equalsClick(clickedView: View){
        //ყოველთვის როდესაც ტოლობის ღილაკს დავაჭერთ ეს ცვლადი გაიზრდება ერთით
        //ეს დაგვეხმარება დავადგინოთ ზუსტად რამდენჯერ დაეჭირა ტოლობის ღილაკს

        equalityExecutionCount++

        //თუ მომხმარებელი აკრიფავს 5 ს დააჭერს პლიუსს და შემდეგ ტოლობას
        //resultTextView ს ტექსტი იქნება ცარიელი და რიცხვად გადაყვანის დროს კალკულატორი დაიქრაშება
        //ცარიელი ტექსტის მაგივრად equalsClick ში შემოვიტანოთ ნული.
        if(resultTextView.text == "")
        {
            resultTextView.text = "0"
        }

        var secondOperand = resultTextView.text.toString().toDouble()
        //თუ ტოლობის ღილაკს დაეჭირა ერთხელ მეორე ციფრი შეინახება ცვლადში
        if(equalityExecutionCount == 1 ){
            toSaveOperand = secondOperand
        }
        //თუ ტოლობის ღილაკს დაეჭირა 2 ჯერ ან მეტჯერ მიღებულ შედეგსა და ახალ ციფრს შეეცვლებათ ადგილები
        // ეს ყველაფერი გვეხმარება შემდეგში: თუ მაგალითად გვაქვს 20 - 5 ის ოპერაცია, პირველი ტოლობის
        //დაჭერის შემდეგ პასუხი იქნება თხუთმეტი. ციფრი 5 ანუ საკლები ინახება toSaveOperand ცვლადში და
        // და რადგან ახალი resultTextView უკვე თხუთმეტია და არა ციფრი ხუთი, საჭიროა მათი ადგილების გადანაცვლება
        // რომ შემდეგი ტოლობის ღილაკის განმეორებითი დაჭერის შემდეგ 15-ს გამოაკლდეს 5, 10 ს გამოაკლდეს 5 და ა.შ.
        if(equalityExecutionCount > 1) {
            operand = secondOperand
            secondOperand = toSaveOperand
        }
        when (operation){
            "+" -> resultTextView.text = (operand + secondOperand).toString()
            "-" -> resultTextView.text = (operand  - secondOperand).toString()
            "*" -> resultTextView.text = (operand * secondOperand).toString()
            "/" -> resultTextView.text = (operand / secondOperand).toString()
            "%" -> resultTextView.text = (operand * secondOperand/100).toString()
        }
        //რადგან მიღებული რიცხვი არის double ის ტიპის 5 + 5 დაიბეჭდება როგორც
        //10.0. ამიტომ საჭიროა .0 ჩამოვაშოროთ
        if(resultTextView.text.toString().endsWith(".0")){
            resultTextView.text = resultTextView.text.toString().dropLast(2)
        }
        //ტოლობის ღილაკის დაჭერის შემდეგ ეს ცვლადი გახდება true
        startANew = true


    }
    fun clearThem(clickedView: View) {
        if (clickedView is TextView) {
            resultTextView.text = resultTextView.text.toString()
            resultTextView.text = "0"
            operand = 0.0
            operation = ""
        }
    }

    fun delThem(clickedView: View) {
        if (clickedView is TextView) {

            var result = resultTextView.text.toString().dropLast(1)
            if(resultTextView.text.length == 1){
                result = "0"
                operand = 0.0
                operation =""
            }
            resultTextView.text = result
        }
    }

}