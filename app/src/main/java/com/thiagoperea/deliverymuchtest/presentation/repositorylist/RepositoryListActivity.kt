package com.thiagoperea.deliverymuchtest.presentation.repositorylist

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import com.thiagoperea.deliverymuchtest.R
import org.koin.android.viewmodel.ext.android.viewModel

class RepositoryListActivity : AppCompatActivity() {

    val viewModel by viewModel<RepositoryListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_list)
        setupToolbar()
        setupSearchBar()
    }

    private fun setupSearchBar() {
        findViewById<TextInputLayout>(R.id.repositoryListSearchField).apply {
            setEndIconOnClickListener {
                viewModel.doSearch(this.editText?.text.toString())
                toast("fazer pesquisa")
            }

            editText?.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.doSearch(this.editText?.text.toString())
                    this@RepositoryListActivity.toast("fazer pesquisa")

                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }

    }

    private fun setupToolbar() {
        findViewById<MaterialToolbar>(R.id.repositoryListToolbar)?.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.dayNightMenu) {
                //TODO: ViewModel.switchDayNight
                viewModel.switchDayNight()
                toast("mudar tema")
            }
            return@setOnMenuItemClickListener true
        }
    }
}

fun AppCompatActivity.toast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()