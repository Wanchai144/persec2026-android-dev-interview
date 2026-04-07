package com.example.persec2026_android_dev_interview_wanchai

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.text.iterator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        runAllTests()
    }

    private fun runAllTests() {


        Log.d("Q1", "=== Q1: isBalanced ===")
        Log.d("Q1", "()"       + " => " + isBalanced("()"))
        Log.d("Q1", "([{)"     + " => " + isBalanced("([{)"))
        Log.d("Q1", "([]]"     + " => " + isBalanced("([]]"))
        Log.d("Q1", "([{}])"   + " => " + isBalanced("([{}])"))
        Log.d("Q1", "([[{}]]]" + " => " + isBalanced("([[{}]]]"))
        Log.d("Q1", ")"        + " => " + isBalanced(")"))
        Log.d("Q1", "(]}])"    + " => " + isBalanced("(]}])"))
        Log.d("Q1", "([)]"     + " => " + isBalanced("([)]"))
        Log.d("Q1", "{"        + " => " + isBalanced("{"))


        Log.d("Q2", "=== Q2: sortStrings ===")
        Log.d("Q2", sortStrings(listOf("TH19", "SG20", "TH2")).toString())
        Log.d("Q2", sortStrings(listOf("TH10", "TH3Netflix", "TH1", "TH7")).toString())


        Log.d("Q3", "=== Q3: autocomplete ===")
        Log.d("Q3", autocomplete("th", listOf("Mother", "Think", "Worthy", "Apple", "Android"), 2).toString())
        Log.d("Q3", autocomplete("th", listOf("Mother", "Think", "Worthy", "Apple", "Android"), 3).toString())

        Log.d("Q4", "=== Q4: Roman Numerals ===")
        Log.d("Q4", "1989 => " + intToRoman(1989))
        Log.d("Q4", "2000 => " + intToRoman(2000))
        Log.d("Q4", "68   => " + intToRoman(68))
        Log.d("Q4", "109  => " + intToRoman(109))
        Log.d("Q4", "MCMLXXXIX => " + romanToInt("MCMLXXXIX"))
        Log.d("Q4", "MM        => " + romanToInt("MM"))
        Log.d("Q4", "LXVIII    => " + romanToInt("LXVIII"))
        Log.d("Q4", "CIX       => " + romanToInt("CIX"))

        Log.d("Q5", "=== Q5: sortDigitsDescending ===")
        Log.d("Q5", "3008 => " + sortDigitsDescending(3008))
        Log.d("Q5", "1989 => " + sortDigitsDescending(1989))
        Log.d("Q5", "2679 => " + sortDigitsDescending(2679))
        Log.d("Q5", "9163 => " + sortDigitsDescending(9163))

        Log.d("Q6", "=== Q6: tribonacci ===")
        Log.d("Q6", tribonacci(listOf(1, 3, 5), 5).toString())
        Log.d("Q6", tribonacci(listOf(2, 2, 2), 3).toString())
        Log.d("Q6", tribonacci(listOf(10, 10, 10), 4).toString())
        Log.d("Q6", tribonacci(listOf(1, 1, 1), 6).toString())
    }


    private fun isBalanced(input: String): Boolean {
        val stack = ArrayDeque<Char>()
        val matchingClose = mapOf(')' to '(', ']' to '[', '}' to '{')
        for (ch in input) {
            when (ch) {
                '(', '[', '{' -> stack.addLast(ch)
                ')', ']', '}' -> {
                    if (stack.isEmpty() || stack.last() != matchingClose[ch]) return false
                    stack.removeLast()
                }
            }
        }
        return stack.isEmpty()
    }


    private fun sortStrings(items: List<String>): List<String> {
        val regex = Regex("^([A-Za-z]+)(\\d+)(.*)\$")
        return items.sortedWith(Comparator { a, b ->
            val matchA = regex.find(a)
            val matchB = regex.find(b)
            if (matchA != null && matchB != null) {
                val prefixCmp = matchA.groupValues[1].compareTo(matchB.groupValues[1], ignoreCase = true)
                if (prefixCmp != 0) return@Comparator prefixCmp
                val numCmp = matchA.groupValues[2].toInt().compareTo(matchB.groupValues[2].toInt())
                if (numCmp != 0) return@Comparator numCmp
                matchA.groupValues[3].compareTo(matchB.groupValues[3], ignoreCase = true)
            } else {
                a.compareTo(b, ignoreCase = true)
            }
        })
    }

    private fun autocomplete(search: String, items: List<String>, maxResult: Int): List<String> {
        val lower = search.lowercase()
        val matched = items.filter { it.lowercase().contains(lower) }
        val starts = matched.filter { it.lowercase().startsWith(lower) }
        val middle = matched.filter {
            val idx = it.lowercase().indexOf(lower)
            idx > 0 && idx < it.length - lower.length
        }
        val ends = matched.filter {
            it.lowercase().endsWith(lower) && !it.lowercase().startsWith(lower)
        }
        return (starts + middle + ends).distinct().take(maxResult)
    }

    private fun intToRoman(num: Int): String {
        val values  = intArrayOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
        val symbols = arrayOf("M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I")
        var n = num
        return buildString {
            for (i in values.indices) {
                while (n >= values[i]) {
                    append(symbols[i])
                    n -= values[i]
                }
            }
        }
    }

    private fun romanToInt(s: String): Int {
        val map = mapOf('I' to 1, 'V' to 5, 'X' to 10, 'L' to 50,
            'C' to 100, 'D' to 500, 'M' to 1000)
        var result = 0
        for (i in s.indices) {
            val curr = map[s[i]] ?: 0
            val next = if (i + 1 < s.length) map[s[i + 1]] ?: 0 else 0
            result += if (curr < next) -curr else curr
        }
        return result
    }

    // ── Q5 ────────────────────────────────────────────────────
    private fun sortDigitsDescending(num: Int): Int {
        return num.toString()
            .toList()
            .sortedDescending()
            .joinToString("")
            .toInt()
    }

    private fun tribonacci(initial: List<Int>, n: Int): List<Int> {
        if (n == 0) return emptyList()
        val seq = initial.toMutableList()
        while (seq.size < n) {
            val size = seq.size
            val next = when {
                size == 0 -> 0
                size == 1 -> seq[0]
                size == 2 -> seq[0] + seq[1]
                else      -> seq[size - 1] + seq[size - 2] + seq[size - 3]
            }
            seq.add(next)
        }
        return seq.take(n)
    }
}
